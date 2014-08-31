package selectParser.parser;

public class ASTRegularCondition extends SimpleNode {
	private String oper = null;

	public String toString() {
		return "ASTRegularCondition:oper=" + oper;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public ASTRegularCondition(int id) {
		super(id);
	}

	public ASTRegularCondition(SelectParser p, int id) {
		super(p, id);
	}
  public Object jjtAccept(SelectParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}

