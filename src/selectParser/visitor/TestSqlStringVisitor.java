package selectParser.visitor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import selectParser.parser.ASTSelect;
import selectParser.parser.ParseException;
import selectParser.parser.SelectParser;

@SuppressWarnings("unchecked")
public class TestSqlStringVisitor extends TestCase {
	public void testVisitor() throws ParseException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());
		SelectParser p = null;
		System.out.println("Reading from buffer");

		String statement = "SELECT table1.*, func(a) u,b b1,(select i from v where u=mytable.d) xx FROM (select i from v where u=mytable.d) mytable1,mytable2 x where a=$p(name=u, x=\"yyyy-mm-dd\") and x between -1 and 11 and (u=v or x=u)";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}

	public void testVisitor2() throws ParseException {
		List list = new ArrayList();
		ParameterSqlVisitor v = new ParameterSqlVisitor(new StringBuffer(), list, true);
		HashMap funcs = new HashMap();
		funcs.put("p", new UDFFunction() {

			public Object invoke(Map parameters) throws ParameterHasNoValueException,
					ParameterNotFoundException {
				return "aaa";
			}
		});
		v.setUdfFunctions(funcs);
		SelectParser p = null;
		System.out.println("Reading from buffer");
		// String statement = "SELECT *,u.*,u.a,b,(select i from v where
		// u=mytable.d) FROM mytable WHERE u>2 LIMIT 3, ?";
		String statement = "SELECT func(a) u,b b1,(select i from v where u=mytable.d) xx FROM (select i from v where u=$p()) mytable1,mytable2 x where a=$p(name=u, x=\"yyyy-mm-dd\") and x between -1 and 11 and (u=v or x=u)";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
		List parameters = v.getParameters();

		System.out.println(parameters);
	}

	public void testVisitor3() throws ParseException {
		List list = new ArrayList();
		ParameterSqlVisitor v = new ParameterSqlVisitor(new StringBuffer(), list, true);
		HashMap funcs = new HashMap();
		funcs.put("p", new UDFFunction() {

			public Object invoke(Map parameters) throws ParameterHasNoValueException,
					ParameterNotFoundException {
				return "aaa";
			}
		});
		v.setUdfFunctions(funcs);
		SelectParser p = null;
		System.out.println("Reading from buffer");
		// String statement = "SELECT *,u.*,u.a,b,(select i from v where
		// u=mytable.d) FROM mytable WHERE u>2 LIMIT 3, ?";
		String statement = "select * from sys_account where account_id=(select max(account_id) from sys_account where account_status=1 )";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
		List parameters = v.getParameters();

		System.out.println(parameters);
	}

	public void testVisitor4() throws ParseException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());

		SelectParser p = null;
		// String statement = "SELECT *,u.*,u.a,b,(select i from v where
		// u=mytable.d) FROM mytable WHERE u>2 LIMIT 3, ?";
		String statement = "SELECT func(a) " + "FROM mytable "
				+ "left join mytable2 on a=x" + "";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());

	}

	public void testVisitor5() throws ParseException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());

		SelectParser p = null;
		// String statement = "SELECT *,u.*,u.a,b,(select i from v where
		// u=mytable.d) FROM mytable WHERE u>2 LIMIT 3, ?";
		// String statement = "SELECT a,b " + "FROM mytable "
		// + "group by 1,2 having count(x)>1";

		String statement = " SELECT DISTINCT chanl_flow_no, a.cust_no,a.chanl_no,amt1,CONCAT(a.trad_date,a.trad_time)AS trad_times "
				+ " 	FROM cust_grade_hist a LEFT JOIN call_out_list cl "
				+ " 	ON a.chanl_flow_no=cl.flow_no  "
				+ " 				WHERE a.cust_no IN(SELECT t.cust_no FROM cust_grade_hist t WHERE t.chanl_cust_no=$C(name=CHL_CUST_NO)) "
				+ " 	ORDER BY trad_times DESC ";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());

	}

	public void testVisitor6() throws ParseException {
		List list = new ArrayList();
		ParameterSqlVisitor v = new ParameterSqlVisitor(new StringBuffer(), list, false);
		HashMap funcs = new HashMap();
		funcs.put("C", new UDFFunction() {

			public Object invoke(Map parameters) throws ParameterHasNoValueException,
					ParameterNotFoundException {
				return "aaa";
			}
		});
		v.setUdfFunctions(funcs);
		SelectParser p = null;
		System.out.println("Reading from buffer");
		// String statement = "SELECT *,u.*,u.a,b,(select i from v where
		// u=mytable.d) FROM mytable WHERE u>2 LIMIT 3, ?";
		String statement = " SELECT DISTINCT chanl_flow_no, a.cust_no,a.chanl_no,amt1,CONCAT(a.trad_date,a.trad_time)AS trad_times "
				+ " 	FROM cust_grade_hist a LEFT JOIN call_out_list cl "
				+ " 	ON a.chanl_flow_no=cl.flow_no  "
				+ " 				WHERE a.cust_no IN(SELECT t.cust_no FROM cust_grade_hist t WHERE t.chanl_cust_no is not null) "
				+ " 	ORDER BY trad_times asc";

		// String statement = "SELECT func(a) u,b b1,(select i from v where
		// u=mytable.d) xx FROM (select i from v where u=$p()) mytable1,mytable2
		// x where a=$p(name=u, x=\"yyyy-mm-dd\") and x between -1 and 11 and
		// (u=v or x=u)";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
		List parameters = v.getParameters();

		System.out.println(parameters);

	}

	public void testVisitor7() throws ParseException {
		List list = new ArrayList();
		ParameterSqlVisitor v = new ParameterSqlVisitor(new StringBuffer(), list, false);

		SelectParser p = null;
		System.out.println("Reading from buffer");
		// String statement = "SELECT *,u.*,u.a,b,(select i from v where
		// u=mytable.d) FROM mytable WHERE u>2 LIMIT 3, ?";
		String statement = "select sys_account.* from sys_account";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
		List parameters = v.getParameters();

		System.out.println(parameters);
	}

	public void testVisitor8() throws ParseException {
		List list = new ArrayList();
		ParameterSqlVisitor v = new ParameterSqlVisitor(new StringBuffer(), list, false);

		SelectParser p = null;
		System.out.println("Reading from buffer");
		// String statement = "SELECT *,u.*,u.a,b,(select i from v where
		// u=mytable.d) FROM mytable WHERE u>2 LIMIT 3, ?";
		String statement = "select sys_account.* from sys_account where a=1 and (b=2 or c=3)";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
		List parameters = v.getParameters();

		System.out.println(parameters);
	}

	public void testVisitor9() throws ParseException {
		List list = new ArrayList();
		ParameterSqlVisitor v = new ParameterSqlVisitor(new StringBuffer(), list, false);
		HashMap map = new HashMap();
		map.put("x", new UDFFunction() {

			public Object invoke(Map parameters) throws ParameterHasNoValueException,
					ParameterNotFoundException {

				return new String[] { "xxxx", "yyyy" };
			}
		});
		v.setUdfFunctions(map);
		SelectParser p = null;
		System.out.println("Reading from buffer");
		// String statement = "SELECT *,u.*,u.a,b,(select i from v where
		// u=mytable.d) FROM mytable WHERE u>2 LIMIT 3, ?";
		String sqlString = "select account_id, account_name "
				+ "from sys_account "
				+ "where account_id = 'admin' and (3+4-4/(4-1)) = func($x(a=\"bb.cc\"))+$x(a=\"123\") group by account_id having account_id>func(2)";

		ByteArrayInputStream is = new ByteArrayInputStream(sqlString.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
		List parameters = v.getParameters();

		System.out.println(parameters);
	}

	public void testVisitor10() throws ParseException {
		List list = new ArrayList();
		ParameterSqlVisitor v = new ParameterSqlVisitor(new StringBuffer(), list, false);
		v.setExtractor(new SqlConditionExtractor() {
			
			@Override
			public void doExtract(StringBuffer buffer, List<Object> parameters) {
				buffer.append("aaa=?");
				parameters.add("12345");				
			}
		});
		
		HashMap map = new HashMap();
		map.put("x", new UDFFunction() {

			public Object invoke(Map parameters) throws ParameterHasNoValueException,
					ParameterNotFoundException {

				return "xxxx";
			}
		});
		v.setUdfFunctions(map);
		SelectParser p = null;
		System.out.println("Reading from buffer");
		// String statement = "SELECT *,u.*,u.a,b,(select i from v where
		// u=mytable.d) FROM mytable WHERE u>2 LIMIT 3, ?";
		String sqlString = "(select account_id, account_name "
				+ "from sys_account) " + "union "
				+ " (select account_id, account_name " + "from sys_account) "
				+ "order by aaa";

		ByteArrayInputStream is = new ByteArrayInputStream(sqlString.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
		List parameters = v.getParameters();

		System.out.println(parameters);
	}

	public void testVisitor11() throws ParseException {
		List list = new ArrayList();
		ParameterSqlVisitor v = new ParameterSqlVisitor(new StringBuffer(), list, false);
		v.setExtractor(new SqlConditionExtractor() {
			
			@Override
			public void doExtract(StringBuffer buffer, List<Object> parameters) {
				buffer.append("aaa=?");
				parameters.add("12345");				
			}
		});

		HashMap map = new HashMap();
		map.put("x", new UDFFunction() {

			public Object invoke(Map parameters) throws ParameterHasNoValueException,
					ParameterNotFoundException {

				return "xxxx";
			}
		});
		v.setUdfFunctions(map);
		SelectParser p = null;
		System.out.println("Reading from buffer");
		// String statement = "SELECT *,u.*,u.a,b,(select i from v where
		// u=mytable.d) FROM mytable WHERE u>2 LIMIT 3, ?";

		String sqlString = "select *  from RULE_DESC_INFO, "
				+ "((select c.*, '1' as his from CALL_HIST_RULE c )"
				+ " UNION (select cb.*, '2' as his from CALL_HIST_RULE_BAK cb)) abc"
				+ " where RULE_NO = HIST_RULE_NO and Hist_rule_no='105'"
				+ " and RISK_NO like 'abc'";

		ByteArrayInputStream is = new ByteArrayInputStream(sqlString.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
		List parameters = v.getParameters();

		System.out.println(parameters);
	}

	public void test12() {
		String[] a = new String[] { "aaa", "bbb", "ccc" };
		Object obj = a;
		System.out.println(a.getClass().isArray());
		int length = Array.getLength(obj);
		for (int i = 0; i < length; i++) {
			Object ele = Array.get(obj, i);
			System.out.println(ele);
		}
	}

	public void testLeftJoin() throws ParseException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());
		SelectParser p = null;
		System.out.println("Reading from buffer");
		// String statement = "SELECT *,u.*,u.a,b,(select i from v where
		// u=mytable.d) FROM mytable WHERE u>2 LIMIT 3, ?";
		String statement = "select c.*, pc.CUST_NAME"
				+ " from (select b.*, p.CUST_NO"
				+ "       from BC_LOG b"
				+ "       left join PRIV_CHANL_INFO p on b.USERID = p.CHANL_CUST_NO) c"
				+ " left join PRIV_CUST_INFO pc on c.CUST_NO = pc.CUST_NO";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}

	public void testSubQuery() throws ParseException, IOException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());
		SelectParser p = null;
		System.out.println("Reading from buffer");

		StringBuffer statement = new StringBuffer();
		BufferedReader r = new BufferedReader(new InputStreamReader(
				TestSqlStringVisitor.class.getResourceAsStream("Query.txt")));
		String s = null;
		while ((s = r.readLine()) != null) {
			statement.append(s);
		}

		ByteArrayInputStream is = new ByteArrayInputStream(statement.toString()
				.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}

	public void testQuery2() throws ParseException, IOException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());
		SelectParser p = null;
		System.out.println("Reading from buffer");

		StringBuffer statement = new StringBuffer();
		BufferedReader r = new BufferedReader(new InputStreamReader(
				TestSqlStringVisitor.class.getResourceAsStream("Query2.txt")));
		String s = null;
		while ((s = r.readLine()) != null) {
			statement.append(s);
		}

		ByteArrayInputStream is = new ByteArrayInputStream(statement.toString()
				.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}

	public void testChineseQuery() throws ParseException, IOException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());
		SelectParser p = null;
		System.out.println("Reading from buffer");

		String statement = "select c.*"
				+ " from aaa c where a='��' order by aaa,bbb,ccc";

		ByteArrayInputStream is = new ByteArrayInputStream(statement.toString()
				.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}

	public void testxxQuery() throws ParseException, IOException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());
		SelectParser p = null;
		System.out.println("Reading from buffer");

		StringBuffer statement = new StringBuffer();
		BufferedReader r = new BufferedReader(new InputStreamReader(
				TestSqlStringVisitor.class.getResourceAsStream("Query3.txt")));
		String s = null;
		while ((s = r.readLine()) != null) {
			statement.append(s + "\n");
		}

		ByteArrayInputStream is = new ByteArrayInputStream(statement.toString()
				.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}

	public void testVisitorLeftJoin1() throws ParseException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());
		SelectParser p = null;
		System.out.println("Reading from buffer");
		String statement = "SELECT distinct a FROM mytable1 left join ss left join sss,mytable2 where a(+)=b and x between 1 and 11 and (u=v or x=u)";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}

	public void testVisitorLeftJoin2() throws ParseException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());
		SelectParser p = null;
		System.out.println("Reading from buffer");
		String statement = "SELECT distinct a FROM mytable1 ,mytable2 left join ss left join sss where a(+)=b and x between 1 and 11 and (u=v or x=u)";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}

	public void testVisitorLeftExternalLink1() throws ParseException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());
		SelectParser p = null;
		System.out.println("Reading from buffer");
		String statement = "SELECT distinct a FROM mytable1 ,mytable2  where a(+)=b and x between 1 and 11 and (u=v or x=u)";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}

	public void testVisitorRightExternalLink() throws ParseException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());
		SelectParser p = null;
		System.out.println("Reading from buffer");
		String statement = "SELECT distinct a FROM mytable1 ,mytable2  where a=b(+) and x between 1 and 11 and (u=v or x=u)";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}

	public void testVisitorStringAdd() throws ParseException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());
		SelectParser p = null;
		System.out.println("Reading from buffer");
		// String statement = "SELECT distinct a FROM mytable1 ,mytable2 where
		// a='ab'||'cd' and x between 1 and 11 and (u=v or x=u)";
		String statement = "select sys_account.account_id,account_status,account_inv_date,role_id "
				+ "from (select * from tab1 union all select * from tab2) where "
				+ "not sys_account.account_id = sys_accountrole.account_id(+)";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);

		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}

	public void testVisitorCase() throws ParseException {
		SqlStringVisitor v = new SqlStringVisitor(new StringBuffer());
		SelectParser p = null;
		System.out.println("Reading form buffer");
		
		String statement = "select * from (select p.appraisal_sn,p.appraisal_name,p.start_date,p.end_date,p.self_finish_date, p.last_finish_date,p.begin_date, is_self_timeout, p.review_finish_date,p.remark,p.appraisal_status,u.user_id,n.nurse_sn,n.is_can_edit,n.nurse_appraisal_sn from  t_nurse_appraisal n left join t_performance_appraisal p  on p.appraisal_sn = n.appraisal_sn left join sys_user u on u.emp_id = n.nurse_sn ) t_performance_appraisal where 1=1";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);
		
		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}
	
	public void testUDFProxy() throws ParseException {
		SqlStringVisitor v = new ParameterSqlVisitor(new StringBuffer(), new ArrayList<Object>(), false);
		SelectParser p = null;
		System.out.println("Reading form buffer");
		
		String statement = "SELECT t_nurse_info.*, group_name FROM t_nurse_info LEFT JOIN sys_group ON t_nurse_info.group_no=sys_group.group_no WHERE t_nurse_info.nurse_sn NOT IN (SELECT DISTINCT  nurse_no FROM t_nurse_group WHERE nurse_no IS NOT NULL AND t_nurse_group.group_no=$C(name=ACCOUNT_STATUS))";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);
		
		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}
	
	public void testLimit() throws ParseException {
		SqlStringVisitor v = new ParameterSqlVisitor(new StringBuffer(), new ArrayList<Object>(), false);
		SelectParser p = null;
		System.out.println("Reading form buffer");
		
		String statement = "select top 5 from aaa";
		ByteArrayInputStream is = new ByteArrayInputStream(statement.getBytes());
		p = new SelectParser(is);
		
		ASTSelect t = p.Select();
		t.jjtAccept(v, null);
		System.out.println(v.getBuffer());
	}
}
