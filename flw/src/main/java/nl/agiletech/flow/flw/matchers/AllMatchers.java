package nl.agiletech.flow.flw.matchers;

import nl.agiletech.flow.flw.http.DefaultResourceMatcher;
import nl.agiletech.flow.flw.http.ResourceMatcher;

public final class AllMatchers {
	public static final String PROP_APIMETHOD = "requesturi:apiMethod";
	public static final String PROP_SESSIONID = "requesturi:sessionId";
	public static final String PROP_PROJECTNAME = "requesturi:projectName";
	public static final String PROP_RESOURCENAME = "requesturi:resourceName";

	/**
	 * Matches: POST /update/{sessionId}/{projectName}/*
	 * 
	 * @return
	 */
	public static ResourceMatcher createUpdateMatcher() {
		DefaultResourceMatcher rm = new DefaultResourceMatcher();
		rm.setMatchMethod("post");
		rm.addLiteralSegment("update", PROP_APIMETHOD);
		rm.addSubstitutionSegment(PROP_SESSIONID);
		rm.addSubstitutionSegment(PROP_PROJECTNAME);
		return rm;
	}

	/**
	 * Matches: POST /resource/{sessionId}/{projectName}/{resourceName}/*
	 * 
	 * @return
	 */
	public static ResourceMatcher createResourceMatcher() {
		DefaultResourceMatcher rm = new DefaultResourceMatcher();
		rm.setMatchMethod("post");
		rm.addLiteralSegment("resource", PROP_APIMETHOD);
		rm.addSubstitutionSegment(PROP_SESSIONID);
		rm.addSubstitutionSegment(PROP_PROJECTNAME);
		rm.addSubstitutionSegment(PROP_RESOURCENAME);
		return rm;
	}
}
