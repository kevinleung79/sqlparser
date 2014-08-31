package selectParser.parser;

public class ASTOrderByElement extends SimpleNode {
	private boolean asc = false;
  public ASTOrderByElement(int id) {
    super(id);
  }

  public ASTOrderByElement(SelectParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SelectParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

public boolean isAsc() {
	return asc;
}

public void setAsc(boolean asc) {
	this.asc = asc;
}
}
