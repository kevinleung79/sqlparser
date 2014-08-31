package selectParser.parser;

public class ASTTop extends SimpleNode {
	private long rowCount;

	private boolean rowCountJdbcParameter = false;

	public ASTTop(int id) {
		super(id);
	}

	public ASTTop(SelectParser p, int id) {
		super(p, id);
	}



	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public boolean isRowCountJdbcParameter() {
		return rowCountJdbcParameter;
	}

	public void setRowCountJdbcParameter(boolean rowCountJdbcParameter) {
		this.rowCountJdbcParameter = rowCountJdbcParameter;
	}

	public String toString() {
		return "Top:rowCount="+rowCount+",rowCountJdbcParameter="+rowCountJdbcParameter;
	}
	  public Object jjtAccept(SelectParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
