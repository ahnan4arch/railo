package railo.transformer.bytecode.op;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

import railo.runtime.exp.TemplateException;
import railo.transformer.bytecode.BytecodeContext;
import railo.transformer.bytecode.BytecodeException;
import railo.transformer.bytecode.Position;
import railo.transformer.bytecode.expression.ExpressionBase;
import railo.transformer.bytecode.util.Methods;
import railo.transformer.bytecode.util.Types;
import railo.transformer.expression.ExprDouble;
import railo.transformer.expression.Expression;
import railo.transformer.expression.literal.Literal;

public final class OpNegateNumber extends ExpressionBase implements ExprDouble {

	private ExprDouble expr;
	

	public static final int PLUS = 0;
	public static final int MINUS = 1;

	private OpNegateNumber(Expression expr, Position start, Position end) {
        super(expr.getFactory(),start,end);
        this.expr=expr.getFactory().toExprDouble(expr);
    }
    
    /**
     * Create a String expression from a Expression
     * @param left 
     * @param right 
     * 
     * @return String expression
     * @throws TemplateException 
     */
    public static ExprDouble toExprDouble(Expression expr, Position start, Position end) {
        if(expr instanceof Literal) {
        	Double d=((Literal) expr).getDouble(null);
        	if(d!=null) {
        		return expr.getFactory().createLitDouble(-d.doubleValue(),start,end);
        	}
        }
        return new OpNegateNumber(expr,start,end);
    }
    
    public static ExprDouble toExprDouble(Expression expr, int operation, Position start, Position end) {
    	if(operation==MINUS) return toExprDouble(expr, start,end);
    	return expr.getFactory().toExprDouble(expr);
    }
	
	
	/**
	 *
	 * @see railo.transformer.bytecode.expression.ExpressionBase#_writeOut(org.objectweb.asm.commons.GeneratorAdapter, int)
	 */
	public Type _writeOut(BytecodeContext bc, int mode) throws BytecodeException {
		GeneratorAdapter adapter = bc.getAdapter();
    	if(mode==MODE_REF) {
            _writeOut(bc,MODE_VALUE);
            adapter.invokeStatic(Types.CASTER,Methods.METHOD_TO_DOUBLE_FROM_DOUBLE);
            return Types.DOUBLE;
        }
    	
    	expr.writeOut(bc, MODE_VALUE);
    	adapter.visitInsn(Opcodes.DNEG);
    	

        return Types.DOUBLE_VALUE;
	}
}
