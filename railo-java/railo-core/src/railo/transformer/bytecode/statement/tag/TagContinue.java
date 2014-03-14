package railo.transformer.bytecode.statement.tag;

import railo.transformer.Factory;
import railo.transformer.Position;
import railo.transformer.TransformerException;
import railo.transformer.bytecode.BytecodeContext;
import railo.transformer.bytecode.Statement;
import railo.transformer.bytecode.statement.FlowControl;
import railo.transformer.bytecode.statement.FlowControlFinal;
import railo.transformer.bytecode.util.ASMUtil;

public final class TagContinue extends TagBase {

	private String label;

	public TagContinue(Factory f, Position start, Position end) {
		super(f,start,end);
		setHasFlowController(true);
	}

	@Override
	public void _writeOut(BytecodeContext bc) throws TransformerException {
		ASMUtil.leadFlow(bc,this,FlowControl.CONTINUE,label);
	}
	
	@Override
	public void setParent(Statement parent) {
		super.setParent(parent);
		parent.setHasFlowController(true);
	}
	
	@Override
	public FlowControlFinal getFlowControlFinal() {
		return null;
	}

	public void setLabel(String label) {
		this.label=label;
	}

}
