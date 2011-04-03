package edu.fiu.cis.indexingStreams.signatureTree;

/**
 * Interface for a signature
 * @author <a href="mailto:oscar.valdivia@cis.fiu.edu">Oscar Valdivia</a>
 */

public interface Signature {
	/**
	 * Returns true if all ones in signature match ones in both signatures
	 * @param signature
	 * @return
	 */
	public boolean match(Signature signature);
	
	/**
	 * Sets text in the signature
	 * @param text string
	 */
	public void setText(String text);
	
	/**
	 * Length of signature
	 * @return length
	 */
	public int length();
	
	/**
	 * Clears signature by setting everything to false
	 */
	public void clear();
	
	/**
	 * If signature is equal to the signature passed in the parameter
	 * 
	 * @param Signature
	 *            to compare
	 * @return True if both have the values
	 */
	public boolean equals(Signature signature);
	
	/**
	 * Disjunction of signatures
	 * 
	 * @param SignatureBitMap
	 *            to or
	 * @return signatures ored
	 */
	public Signature or(Signature signature);
	
	/**
	 * Conjunction of signatures
	 * 
	 * @param SignatureBitMap
	 *            to and
	 * @return signatures anded
	 */
	public Signature and(Signature signature);
	
	/**
	 * Negation of signature
	 * 
	 * @return negation of signature
	 */
	public Signature not();
	
	/**
	 * Calculates Hamming distance between signatures
	 * 
	 * @param SignatureBitMap
	 *            to compare
	 * @return weight increase distance of signatures
	 */
	public int hammingDistance(Signature signature);
	
	/**
	 * Calculates weight increase distance between signatures
	 * 
	 * @param Signature
	 *            to compare
	 * @return weight increase distance of signatures
	 */
	public int epsilon(Signature signature);
	
	/**
	 * Has ones in the same position
	 * 
	 * @param Signature
	 *            to compare
	 * @return True if both have the values
	 */
	public boolean similar(Signature signature);
	
	/**
	 * returns the number of ones in signature
	 * @return weight
	 */
	public int weight();
	
	/**
	 * returns the signature's density. Number of ones ratio to length
	 * @return density
	 */
	public float density();
	
	/**
	 * prints signature to a stringBuffer
	 * @return a stringBuffer
	 */
	public StringBuffer printBuffer();
	
	/**
	 * Size in bytes of this signature
	 * @return size
	 */
	public int sizeBytes();
	
	/**
	 * Compare this signature with the given parameter
	 * @param signature
	 * @return 0 if they are equal<br>
	 * 1 if this signature is greater than parameter<br>
	 * -1 if this signature is less than parameter
	 */
	public int compare(Signature signature);
}
