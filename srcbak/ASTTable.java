package selectParser.parser;

public class ASTTable extends SimpleNode {
	private String schema = null;

	private String table = null;

	public ASTTable(int id) {
		super(id);
	}

	public ASTTable(SelectParser p, int id) {
		super(p, id);
	}

	public String toString() {
		return "Table:schema=" + schema + ",table=" + table;
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
  public Object jjtAccept(SelectParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

public String getWholeTableName() {
	if(schema !=null){
		return schema+"."+table;
	}else{
		return table;
	}
}
}

