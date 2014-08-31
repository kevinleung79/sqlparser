package selectParser.parser;

public class ASTFunction extends SimpleNode {
	private String name;
	private boolean allColumns = false;
  public ASTFunction(int id) {
    super(id);
  }

  public ASTFunction(SelectParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SelectParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

public boolean isAllColumns() {
	return allColumns;
}

public void setAllColumns(boolean allColumns) {
	this.allColumns = allColumns;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}
}
