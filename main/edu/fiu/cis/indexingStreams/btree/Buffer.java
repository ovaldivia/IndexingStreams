package edu.fiu.cis.indexingStreams.btree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * Buffer for inverted index experiments

 * @author <a href="mailto:oscar.valdivia@cis.fiu.edu">Oscar Valdivia</a>
 */
public class Buffer {
	private TreeMap<String, List> buffer;
	
	public Buffer() {
		this.buffer = new TreeMap();
	}
	
	public void add(String keyword, long id){
		List list = null;
		
		
		try {
			if (buffer.containsKey(keyword)){
				list = buffer.get(keyword);
			}else{
				list = new ArrayList();
			}
			
			
			if (!list.contains(id)){
				list.add(id);
				buffer.put(keyword, list);
			}
		} catch (Exception e) {
			System.out.println("Size of list: " + list.size());
			e.printStackTrace();
		}
		
	}
	
	public Iterator<String> iterator(){
		return buffer.keySet().iterator();
		
	}
	
	public List get(String key){
		return buffer.get(key);
	}
	
	public int size(){
		return buffer.size();
	}
	
	public void clear(){
		buffer.clear();
	}
}
