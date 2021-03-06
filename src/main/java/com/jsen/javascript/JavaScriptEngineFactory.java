/**
 * JavaScriptEngineFactory.java
 * (c) Radim Loskot and Radek Burget, 2013-2014
 *
 * ScriptBox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * ScriptBox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *  
 * You should have received a copy of the GNU Lesser General Public License
 * along with ScriptBox. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.jsen.javascript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jsen.core.AbstractScriptEngine;
import com.jsen.core.AbstractScriptEngineFactory;
import com.jsen.core.GlobalObjectScriptSettings;
import com.jsen.core.ScriptSettings;
import com.jsen.core.annotation.ScriptEngineFactory;
import com.jsen.javascript.injectors.ClassObjectsInjector;
import com.jsen.javascript.injectors.URLInjector;

/**
 * JavaScript engine factory for creating the {@link JavaScriptEngine} instances.
 * 
 * @author Radim Loskot
 * @version 0.9
 * @since 0.9 - 21.4.2014
 */
@ScriptEngineFactory
public class JavaScriptEngineFactory extends AbstractScriptEngineFactory {

	private static List<String> mimeTypes;
	
	static {	   
		/* http://tools.ietf.org/html/rfc4329#page-9 */
		mimeTypes = new ArrayList<String>(4);
		mimeTypes.add("application/javascript");
		mimeTypes.add("application/ecmascript");
		mimeTypes.add("text/javascript");
		mimeTypes.add("text/ecmascript");
		mimeTypes = Collections.unmodifiableList(mimeTypes);
	}
	
	private static final String ENGINE_SHORTNAME = "javascript";
	private static final String ENGINE_NAME = "Window Rhino Engine";
	private static final String ENGINE_VERSION = "0.9";
	private static final String LANGUAGE_NAME = "ECMAScript";
	private static final String LANGUAGE_VERSION = "1.8";
		
	public JavaScriptEngineFactory() {
		registerScriptContextsInject(new ClassObjectsInjector());
		registerScriptContextsInject(new URLInjector());
	}
	
	@Override
	public List<String> getExplicitlySupportedMimeTypes() {
		return mimeTypes;
	}

	@Override
	protected AbstractScriptEngine getBrowserScriptEngineProtected(ScriptSettings scriptSettings) {
		if (scriptSettings instanceof GlobalObjectScriptSettings) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			AbstractScriptEngine engine = new JavaScriptEngine(this, (GlobalObjectScriptSettings)scriptSettings);
			return engine;
		}
		//TODO: Throw sufficient exception
		return null;
	}

	@Override
	public String getEngineName() {
		return ENGINE_NAME;
	}

	@Override
	public String getEngineVersion() {
		return ENGINE_VERSION;
	}

	@Override
	public String getLanguageName() {
		return LANGUAGE_NAME;
	}

	@Override
	public String getLanguageVersion() {
		return LANGUAGE_VERSION;
	}

	@Override
	public String getEngineShortName() {
		return ENGINE_SHORTNAME;
	}
}
