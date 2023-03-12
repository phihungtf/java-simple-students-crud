package hocsinh;

import java.io.*;
import java.util.ArrayList;

public class DanhSachHocSinh {
	private ArrayList<HocSinh> ds = new ArrayList<HocSinh>();

	public void write(DataOutputStream out) throws Exception {
		try {
			for (HocSinh hs : ds) {
				hs.write(out);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void read(DataInputStream in) throws Exception {
		try {
			ds.clear();
			while (true) {
				HocSinh hs = new HocSinh();
				hs.read(in);
				ds.add(hs);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void add() {
		HocSinh hs = new HocSinh();
		ds.add(hs);
	}

	public void remove(int index) {
		ds.remove(index);
	}

	public int size() {
		return ds.size();
	}

	public void clear() {
		ds.clear();
	}

	public Object getValueAt(int row, int col) {
		HocSinh hs = ds.get(row);
		switch (col) {
		case 0:
			return hs.MHS;
		case 1:
			return hs.TenHS;
		case 2:
			return hs.Diem;
		case 3:
			return hs.HinhAnh;
		case 4:
			return hs.DiaChi;
		case 5:
			return hs.GhiChu;
		}
		return null;
	}

	public void setValueAt(Object value, int row, int col) {
		HocSinh hs = ds.get(row);
		switch (col) {
		case 0:
			hs.MHS = (String) value;
			break;
		case 1:
			hs.TenHS = (String) value;
			break;
		case 2:
			hs.Diem = Double.parseDouble((String) value);
			break;
		case 3:
			hs.HinhAnh = (String) value;
			break;
		case 4:
			hs.DiaChi = (String) value;
			break;
		case 5:
			hs.GhiChu = (String) value;
			break;
		}
	}

	public void addFromStrings(String[] values) {
		HocSinh hs = new HocSinh(values[0], values[1], Double.parseDouble(values[2]), values[3], values[4], values[5]);
		ds.add(hs);
	}

	public String getCSVString(int index, String delimiter) {
		return ds.get(index).toCSVString(delimiter);
	}

	@Override
	public String toString() {
		String s = "";
		int i = 0;
		for (HocSinh hs : ds) {
			s += "[" + (i++) + "] " + hs.toString() + "\n";
		}
		return s;
	}
}
