
package auxiliar;

import java.util.Arrays;
import java.util.Collection;

public class WordList {

	private Collection<String>	posWords	= Arrays.asList();
	private Collection<String>	negWords	= Arrays.asList();

	private Collection<String>	posWordsEs	= Arrays.asList();
	private Collection<String>	negWordsEs	= Arrays.asList();


	public Collection<String> getPosWords() {
		return this.posWords;
	}

	public Collection<String> getNegWords() {
		return this.negWords;
	}

	public Collection<String> getPosWordsEs() {
		return this.posWordsEs;
	}

	public Collection<String> getNegWordsEs() {
		return this.negWordsEs;
	}

	public void setPosWords(final Collection<String> posWords) {
		this.posWords = posWords;
	}

	public void setNegWords(final Collection<String> negWords) {
		this.negWords = negWords;
	}

	public void setPosWordsEs(final Collection<String> posWordsEs) {
		this.posWordsEs = posWordsEs;
	}

	public void setNegWordsEs(final Collection<String> negWordsEs) {
		this.negWordsEs = negWordsEs;
	}

}
