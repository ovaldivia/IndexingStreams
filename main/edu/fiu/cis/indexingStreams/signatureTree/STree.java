package edu.fiu.cis.indexingStreams.signatureTree;

import java.io.Serializable;
import java.util.List;

import edu.fiu.cis.indexingStreams.btree.BPage;
import edu.fiu.cis.indexingStreams.btree.Buffer;

/**
 * Implements a S-tree
 * 
 * @author <a href="mailto:oscar.valdivia@cis.fiu.edu">Oscar Valdivia</a>
 * @see S-Tree: A Dynamic Balance Signature Index for 
 *      Office retrieval by U. Deppisch
 * 
 */
public class STree implements Serializable{
	
	private static final long serialVersionUID = -8211367077555330242L;
	private String uid;
	private BPage root;
	
	
	public STree(int numberEntries, int sizeSignature){
		
	}
	
	/**
	 * Creates a signature tree from the given entry buffer
	 * @param buffer entry buffer
	 */
	public STree(Buffer buffer, int numberEntries, int sizeSignature) {
		
	}
	
	/**
	 * Adds a new entry to the tree
	 * @param entry
	 */
	public void add(String keyword){
		//Utils.logInfo(root.printNodeBuffer().toString(),this.getClass());
		
//		Node node = root.searchNodeToBeAdded(entry.getSignature());
//		String id = node.add(entry);
//		
//		
//		if (id!=null){
//			root = load(id);
//		}
		
//		counter++;
		//System.out.println(counter);
	}
	
	/**
	 * Size in bytes of tree
	 * @return size
	 */
	public int sizeBytes(){
		return 0;
	}
	
	public Signature getSignature(){
		return null;
	}
	
	public List search(String keyword){
		return search(new String[]{keyword});
	}
	
	public List search(String[] keywords){
		return null;
	}
	
	public void print(){
		
	}
	
}