package edu.fiu.cis.indexingStreams.signatureTree;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Random;

import edu.fiu.cis.generator.Utils;

/**
 * Implements a bit signature of default size
 * 
 * @author: <a href="mailto:oscar.valdivia@cis.fiu.edu">Oscar Valdivia</a>
 */
public class SignatureBitMap implements Signature, Serializable{
	
	protected BitSet signatureBitmap;
	private final static int NUMBER_BITS_IN_BYTE = 8;
	private final static int SIGNATURE_NUMBER_ONES = 2;
	private final static int DEFAULT_SIZE = 2;
	
	public SignatureBitMap(int size) {
		signatureBitmap = new BitSet(size);
	}
	
	public SignatureBitMap() {
		this(DEFAULT_SIZE);
	}
	
	/**
	 * Returns true if all ones in signature match ones in both signatures
	 * @param signature
	 * @return
	 */
	public boolean match(Signature signature){
		// if different length they don't match;
		if (signature == null || this.signatureBitmap.size() != signature.length())
			return false;

		
		return signature.equals(this);
	}
	
	/**
	 * Sets text in the signature
	 * @param text string
	 */
	public void setText(String text){
		BitSet r = null;
		
		if (text!=null && text.length()>0){
			r = new BitSet(signatureBitmap.size());
			
			String words[] = text.split(" ");
			for (String word: words){
				if (word.length()>0){
					r = or(r, hash(word,SIGNATURE_NUMBER_ONES));
					
				}
			}
		}
		signatureBitmap = r;
	}
	
	//Gets the signature hash for a given string, where is the number of ones
	private BitSet hash(String str, int k) {
		long seed = GeneralHashFunctionLibrary.SDBMHash(str);
		Random random = new Random(seed);
		int N = signatureBitmap.length();
		BitSet r = new BitSet();

		for (int i = 0; i < k; i++) {
			int l = random.nextInt();
			int j = Math.abs(l % N);
			r.set(j);
		}
		return r;
	}
	
	/**
	 * Length of signature
	 * @return length
	 */
	public int length(){
		return signatureBitmap.size();
	}
	
	
	/**
	 * Clears signature by setting everything to false
	 */
	public void clear() {
		
		signatureBitmap.clear();
	}

	/**
	 * If signature is equal to the signature passed in the parameter
	 * 
	 * @param SignatureBitMap
	 *            to compare
	 * @return True if both have the values
	 */
	public boolean equals(Signature signature) {
		// if different length the are not equal;
		if (signature == null || this.signatureBitmap.size() != signature.length())
			return false;

		return ((SignatureBitMap)signature).signatureBitmap.equals(this.signatureBitmap);

	}
	
	

	/**
	 * Disjunction of signatures
	 * 
	 * @param SignatureBitMap
	 *            to or
	 * @return signatures or-ed
	 */
	public SignatureBitMap or(Signature signature) {
		SignatureBitMap rtn = new SignatureBitMap(this.signatureBitmap.size());

		// if different length the are not equal;
		if (signature == null || this.signatureBitmap.size() != signature.length()){
			Utils.logInfo("null or", this.getClass());
			return null;
		}
		
		rtn.signatureBitmap = or(this.signatureBitmap ,((SignatureBitMap)signature).signatureBitmap) ;
		
		return rtn;

	}
	
	//Disjunction of signatures
	private BitSet or(BitSet sig1, BitSet sig2){
		BitSet r = null;
		
		if (sig1!=null && sig2 !=null && sig1.size() > 0 && sig2.size() > 0 ){
			sig1.or(sig2);
			r = sig1;
		}
		
		return r;
		
	}
	
	/**
	 * Conjunction of signatures
	 * 
	 * @param SignatureBitMap
	 *            to and
	 * @return signatures anded
	 */
	public Signature and(Signature signature) {
		
		// if different length the are not equal;
		if (signature == null || this.signatureBitmap.size() != signature.length())
			return null;
		
		((SignatureBitMap)signature).signatureBitmap.and(this.signatureBitmap);
		
		return signature;

	}

	/**
	 * Negation of signature
	 * 
	 * @return negation of signature
	 */
	public Signature not() {
		SignatureBitMap rtn = new SignatureBitMap(this.signatureBitmap.size());
		rtn.signatureBitmap = (this.signatureBitmap);
		
		rtn.signatureBitmap.flip(0, this.signatureBitmap.length() -1);

		return rtn;

	}

	/**
	 * Calculates Hamming distance between signatures
	 * 
	 * @param SignatureBitMap
	 *            to compare
	 * @return weight increase distance of signatures
	 */
	public int hammingDistance(Signature signature) {
		// h = weight (S or S') - weight (S and S')

		return (this.or(signature).weight() - this.and(
				signature).weight());
	}

	/**
	 * Calculates weight increase distance between signatures
	 * 
	 * @param SignatureBitMap
	 *            to compare
	 * @return weight increase distance of signatures
	 */
	public int epsilon(Signature signature) {
		// e = weight (not S and S')
		return this.not().and(signature).weight();
	}

	/**
	 * Has ones in the same position
	 * 
	 * @param SignatureBitMap
	 *            to compare
	 * @return True if both have the values
	 */
	public boolean similar(Signature signature) {
		// if different length the are not equal;
		if (signature == null || this.signatureBitmap.size() != signature.length())
			return false;

		boolean rtn = true;
		for (int i = 0; i < signature.length(); i++)
			if (this.signatureBitmap.get(i) && !((SignatureBitMap)signature).signatureBitmap.get(i)) {
				rtn = false;
				break;
			}
		return rtn;
	}

	/**
	 * returns the number of ones in signature
	 * @return weight
	 */
	public int weight() {
		return signatureBitmap.cardinality();
	}

	/**
	 * returns the signature's density. Number of ones ratio to length
	 * @return density
	 */
	public float density() {
		return (float) weight() / (float) signatureBitmap.size();
	}

	//Returns 1 if true 0 if false
	private int numericValue(boolean val) {
		return val ? 1 : 0;
	}

	/**
	 * prints signature to a stringBuffer
	 * @return a stringBuffer
	 */
	public StringBuffer printBuffer() {
		StringBuffer str = new StringBuffer("[ ");

		for (int i = 0; i < signatureBitmap.size() - 1; i++)
			str.append(numericValue(signatureBitmap.get(i)) + ", ");

		if (signatureBitmap.size() > 0)
			str.append(numericValue(signatureBitmap.get(signatureBitmap.size() - 1)) + " ]");

		return str;
	}
	
	/**
	 * Size in bytes of this signature
	 * @return size
	 */
	public int sizeBytes(){
		return signatureBitmap.size()/ NUMBER_BITS_IN_BYTE;
	}
	
	/**
	 * Compare this signature with the given parameter
	 * @param signature
	 * @return 0 if they are equal<br>
	 * 1 if this signature is greater than parameter<br>
	 * -1 if this signature is less than parameter
	 */
	public int compare(Signature signature) {
		int r = 0;
		
		BitSet c1 = this.signatureBitmap;
		BitSet c2 = ((SignatureBitMap)signature).signatureBitmap;
		 
		// assumes c1 and c2 has the same length
		if (c1 != null && c2 != null) {
			for (int i = 0; i < c1.size(); i++) {
				if (c1.get(i) != c2.get(i)) {
					if (c1.get(i))
						r = 1;
					else
						r = -1;
					break;
				}
			}
		}
		return r;
	}
	

}
