package selectParser.parser;

import java.util.ArrayList;
import java.util.List;

public class ASTAdd extends SimpleNode {
	private List opers = new ArrayList();

	public ASTAdd(int id) {
		super(id);
	}

	public ASTAdd(SelectParser p, int id) {
		super(p, id);
	}

	/** Accept the visitor. * */
	public Object jjtAccept(SelectParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public List getOpers() {
		return opers;
	}

	public void setOpers(List opers) {
		this.opers = opers;
	}
}
