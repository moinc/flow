/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.template;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

/**
 * Interface for template engines.
 * <p>
 * The current contract is simple:
 * <ol>
 * <li>The argument src is a reader that contains text. The full contents
 * obtained through the reader must be written back into the writer, which is
 * specified by dest.</li>
 * <li>The text may contain placeholder instances that have simple syntax. The
 * placeholder starts with a $ character followed by either:
 * <ol>
 * <li>a non-whitespace keyword or</li>
 * <li>an expression that is contained within curly braces.</li>
 * <li>both the keyword and the expression may specify an indexer (e.g.
 * "keyword[3]") in which case the template engine is expected to select an
 * element from an array or collection. The template engine is expected to throw
 * an exception if:
 * <ol>
 * <li>the value specified by the keyword or expression is not an array or
 * collection</li>
 * <li>the indexer specifies an element that is outside of the range of the
 * array or collection</li>
 * </ol>
 * </li>
 * <li>Note that if an indexer is found anywhere else but at the right of the
 * keyword, an exception must be raised.</li>
 * </ol>
 * </li>
 * <li>The keyword or expression may point to an entry in the specified map.
 * </li>
 * <li>The entire placeholder must be replaced by the value obtained from the
 * map.</li>
 * <li>If the keyword or expression is not found in the map, the template engine
 * must throw an exception.</li>
 * <li>The template engine is expected to throw an exception in the following
 * cases:
 * <ol>
 * <li>the keyword or expression is empty.</li>
 * <li>the keyword or expression yields an array or collection but no indexer
 * was specified.</li>
 * <li>the keyword or expression yields an object (i.e. another Map instance).
 * </li>
 * </ol>
 * </li>
 * <li>Values obtained from the map may be any data type including null, if the
 * value is not a string then its string representation is used (i.e. the
 * toString() method is invoked). If a null is encountered, it is replaced by an
 * empty string.</li>
 * </ol>
 * </p>
 * 
 * @author moincreemers
 *
 */
public interface TemplateEngine {
	void render(Reader src, Writer dest, Map<String, Object> dataDictionary)
			throws IOException, TemplateRenderException;
}
