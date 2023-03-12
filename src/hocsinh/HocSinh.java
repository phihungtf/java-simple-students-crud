package hocsinh;

import java.io.*;

class HocSinh {
	public String MHS;
	public String TenHS;
	public double Diem;
	public String HinhAnh;
	public String DiaChi;
	public String GhiChu;

	public HocSinh() {
		this.MHS = "";
		this.TenHS = "";
		this.Diem = 0;
		this.HinhAnh = "";
		this.DiaChi = "";
		this.GhiChu = "";
	}

	public HocSinh(String MHS, String TenHS, double Diem, String HinhAnh, String DiaChi, String GhiChu) {
		this.MHS = MHS;
		this.TenHS = TenHS;
		this.Diem = Diem;
		this.HinhAnh = HinhAnh;
		this.DiaChi = DiaChi;
		this.GhiChu = GhiChu;
	}

	public void write(DataOutputStream out) throws Exception {
		try {
			out.writeUTF(MHS);
			out.writeUTF(TenHS);
			out.writeDouble(Diem);
			out.writeUTF(HinhAnh);
			out.writeUTF(DiaChi);
			out.writeUTF(GhiChu);
		} catch (Exception e) {
			throw e;
		}
	}

	public void read(DataInputStream in) throws Exception {
		try {
			MHS = in.readUTF();
			TenHS = in.readUTF();
			Diem = in.readDouble();
			HinhAnh = in.readUTF();
			DiaChi = in.readUTF();
			GhiChu = in.readUTF();
		} catch (Exception e) {
			throw e;
		}
	}

	public String toCSVString(String delimiter) {
		return MHS + delimiter + TenHS + delimiter + Diem + delimiter + HinhAnh + delimiter + DiaChi + delimiter + GhiChu;
	}

	@Override
	public String toString() {
		return "HocSinh [DiaChi=" + DiaChi + ", Diem=" + Diem + ", GhiChu=" + GhiChu + ", HinhAnh=" + HinhAnh + ", MHS="
				+ MHS + ", TenHS=" + TenHS + "]";
	}
}
