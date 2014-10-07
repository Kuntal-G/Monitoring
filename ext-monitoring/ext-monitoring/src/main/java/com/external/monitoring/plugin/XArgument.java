package com.external.monitoring.plugin;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("arg")
public class XArgument {
	@XStreamAsAttribute
	public int index;

	@XStreamAsAttribute
	public String value;

}
