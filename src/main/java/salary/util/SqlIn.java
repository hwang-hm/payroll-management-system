package salary.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// Oracle IN (?) 플레이스홀더 생성
// Oracle IN (?) プレースホルダ生成
public final class SqlIn {

	private SqlIn() {
	}

	public static String placeholders(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("size must be > 0");
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				sb.append(',');
			}
			sb.append('?');
		}
		return sb.toString();
	}

	public static int setInts(PreparedStatement pstmt, int startIndex, Collection<Integer> ids)
			throws SQLException {
		int i = startIndex;
		for (Integer id : ids) {
			pstmt.setInt(i++, id);
		}
		return i;
	}

	public static List<Integer> nonNull(List<Integer> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		return ids;
	}
}
