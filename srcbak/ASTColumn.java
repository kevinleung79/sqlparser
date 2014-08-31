package selectParser.parser;

public class ASTColumn extends SimpleNode {
	private String schema = null;

	public String toString() {
		return "Column:schema=" + schema + ",table=" + table + ",column="
				+ column;
	}

	private String table = null;

	private String column = null;

	public ASTColumn(int id) {
		super(id);
	}

	public ASTColumn(SelectParser p, int id) {
		super(p, id);
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getWholeColumnName() {

		String columnWholeName = column;
		if (table != null) {
			columnWholeName = table + "." + columnWholeName;
		}
		if (schema != null) {
			columnWholeName = schema + "." + columnWholeName;
		}

		return columnWholeName;

	}

	public Object jjtAccept(SelectParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
