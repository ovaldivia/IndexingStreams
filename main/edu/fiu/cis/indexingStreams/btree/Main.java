package edu.fiu.cis.indexingStreams.btree;



import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import edu.fiu.cis.generator.Event;
import edu.fiu.cis.generator.Generator;
import edu.fiu.cis.generator.GeneratorText;
import edu.fiu.cis.indexingStreams.RecordManager;
import edu.fiu.cis.indexingStreams.RecordManagerFactory;
import edu.fiu.cis.indexingStreams.helper.StringComparator;

/**
 * Inverted Index
 * This is a B+Tree implementation, modified from the original JDBM implementation to handle multiple values per keywords. 
 * The list of values in the leaf nodes are store in separate (outline) files.
 * @author <a href="mailto:oscar.valdivia@cis.fiu.edu">Oscar Valdivia</a>
 */
public class Main {

    static String DATABASE = "btree.db";
    static String BTREE_NAME = "invertedIndex";
    static String PREFIX_EVENT = "event";
    static int keywordId = 0;
    static Buffer buffer = new Buffer();
    static int BUFFER_LIMIT = 10000;

    static String PREFIX = "S";

    /**
     * Main entry-point.
     */
    public static void main( String[] args ) {
        RecordManager recman;
        long          recid;
        
        BTree         tree;
        Properties    props;
        System.out.println("Inverted  Index Started");
        props = new Properties();

        try {
        	Generator generator = new GeneratorText();
        	System.out.println("Generator loaded");
            // open database and setup an object cache
            recman = RecordManagerFactory.createRecordManager( DATABASE, props );

            // try to reload an existing B+Tree
            recid = recman.getNamedObject( BTREE_NAME );
            if ( recid != 0 ) {
                tree = BTree.load( recman, recid );
                System.out.println( "Reloaded existing BTree with " + tree.size()
                                    + " famous people." );
            } else {
                // create a new B+Tree data structure and use a StringComparator
                
                tree = BTree.createInstance( recman, new StringComparator() );
                recman.setNamedObject( BTREE_NAME, tree.getRecid() );
                System.out.println( "Created a new empty BTree" );
            }

            Iterator<Event> iterator = generator.iterator();
            
           
            while (iterator.hasNext()) {
            	Event event = iterator.next();
            	
                insertEvent(event,tree, recman);
            }

            // make the data persistent in the database
            recman.commit();

           

        } catch ( Exception except ) {
            except.printStackTrace();
        }
    }
    
    /**
     * Insert event into a btree
     * @param event to insert
     * @param tree Btree
     * @param recman Record Manager
     */
    
    static void insertEvent(Event event, BTree tree, RecordManager recman){
    	if (event==null)
    		return;
    	
    	System.out.println( "Insert: " + event.getId() );
    	String[] keywords = event.getKeywords();
    	
    	try {
    		for (String keyword: keywords){
    			
    			buffer.add(keyword, event.getId());
    			
    			if (buffer.size() > BUFFER_LIMIT ){
    				System.out.println( "Buffer been emptied" );
    				Iterator<String> iterator = buffer.iterator();
    				
    				while (iterator.hasNext()){
    					String thisKeyword = iterator.next();
    					insertKeyword(thisKeyword, buffer.get(thisKeyword),  tree, recman);
    				}
    				recman.commit();
    				buffer.clear();
    				
    			}
    				
    			
        		
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }
    
    
	@SuppressWarnings("unchecked")
	/**
	 * Insert keywords into tree
	 * @param keyword
	 * @param List of event ids 
	 * @param Btree
	 * @param Record Manager
	 */
	static void insertKeyword(String keyword, List ids, BTree tree, RecordManager recman){
		
    	
    	try {
    		//Attempts to find key word if already in tree
    		Long valueListId = (Long)tree.find(keyword);
    		
    		//If not in tree
    		if (valueListId == null){
    			//Creates a new list and adds the event id
    			List values = new Vector<Integer>();
    	    	values.addAll(ids);
    	    	
    	    	//Saves the list in the record manager and gets the rowid
    	    	valueListId = recman.insert(values);
    	    	
    		}else{
    			
    			List values = (List)recman.fetch(valueListId);
    			values.addAll(ids);
    			
    			recman.update(valueListId, values);
    		}
    		
    		//Insert the keyword with the value of the rowid
	    	tree.insert(keyword, valueListId ,true);
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

    

}
