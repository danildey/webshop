package com.epam.preprod.db.querybuilder;

import java.util.ArrayList;
import java.util.List;

public class SelectBuilder {

	private List<Object> parameters = new ArrayList<>();

	private List<String> columns = new ArrayList<>();

	private List<String> tables = new ArrayList<>();

	private List<String> joins = new ArrayList<>();

	private List<String> leftJoins = new ArrayList<>();

	private List<String> wheres = new ArrayList<>();

	private List<String> orderBys = new ArrayList<>();

	private List<String> groupBys = new ArrayList<>();

	private List<String> havings = new ArrayList<>();

	private int limit;

	private int offset;

	public SelectBuilder() {
	}

	public SelectBuilder(String table) {
		tables.add(table);
		parameters.add(table);
	}

	private void appendList(StringBuilder sql, List<String> list, String init, String sep) {
		boolean first = true;
		for (String s : list) {
			if (first) {
				sql.append(init);
			} else {
				sql.append(sep);
			}
			sql.append(s);
			first = false;
		}
	}

	public SelectBuilder limit(int limit) {
		this.limit = limit;
		parameters.add(limit);
		return this;
	}

	public SelectBuilder offset(int offset) {
		this.offset = offset;
		parameters.add(offset);
		return this;
	}

	public SelectBuilder column(String column) {
		columns.add(column);
		return this;
	}

	public SelectBuilder count() {
		columns.add("COUNT(*) AS count");
		return this;
	}

	public SelectBuilder column(String column, boolean groupBy) {
		columns.add("?");
		parameters.add(column);
		if (groupBy) {
			groupBys.add("?");
			parameters.add(column);
		}
		return this;
	}

	public SelectBuilder from(String from) {
		tables.add(from);
		return this;
	}

	public SelectBuilder groupBy(String groupBy) {
		groupBys.add("?");
		parameters.add(groupBy);
		return this;
	}

	public SelectBuilder having(String having) {
		havings.add("?");
		parameters.add(having);
		return this;
	}

	public SelectBuilder join(String join) {
		joins.add(join);
		return this;
	}

	public SelectBuilder leftJoin(String join) {
		leftJoins.add("?");
		parameters.add(join);
		return this;
	}

	public SelectBuilder orderBy(String orderBy, boolean desc) {
		if (desc) {
			orderBys.add(orderBy + " DESC");
		} else {
			orderBys.add(orderBy + " ASC");
		}
		return this;
	}

	public SelectBuilder where(String expr) {
		wheres.add("?");
		parameters.add(expr);
		return this;
	}

	public SelectBuilder whereLike(String where, String like) {
		wheres.add(where + " LIKE ?");
		parameters.add(like);
		return this;
	}

	public SelectBuilder whereIn(String where, List in) {
		wheres.add(where + inRange(in.size()));
		parameters.addAll(in);
		return this;
	}

	public SelectBuilder whereBetween(String where, int between, int and) {
		wheres.add(where + " BETWEEN ? AND ?");
		parameters.add(between);
		parameters.add(and);
		return this;
	}

	private String inRange(int size) {
		StringBuilder in = new StringBuilder();
		in.append("IN (");
		for (int i = 0; i < size; i++) {
			in.append("?").append(",");
		}
		in.deleteCharAt(in.length() - 1);
		in.append(")");
		return in.toString();
	}

	@Override
	public String toString() {

		StringBuilder sql = new StringBuilder("SELECT ");

		if (columns.size() == 0) {
			sql.append("*");
		} else {
			appendList(sql, columns, "", ", ");
		}

		appendList(sql, tables, " FROM ", ", ");
		appendList(sql, joins, " JOIN ", " JOIN ");
		appendList(sql, leftJoins, " LEFT JOIN ", " LEFT JOIN ");
		appendList(sql, wheres, " WHERE ", " AND ");
		appendList(sql, groupBys, " GROUP BY ", ", ");
		appendList(sql, havings, " HAVING ", " AND ");
		appendList(sql, orderBys, " ORDER BY ", ", ");

		if (limit > 0) {
			sql.append(" LIMIT ?");
			if (offset >= 0) {
				sql.append(" OFFSET  ?");
			}
		}

		return sql.toString();
	}

	public List<Object> getParameters() {
		return parameters;
	}


}