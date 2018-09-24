package in.deepstudio.reports.model;

public class FramingCenterBillReport {

	
	private int id;
	private String billNumber;
	private String billDate;
	private String custName;
	private String quatationBillAmount;
	private String discountAmount;
	private String totalPaidAmount;
	private String dueAmount;
	
	public FramingCenterBillReport(){
			
		}
	
	public FramingCenterBillReport(int id, String billNumber, String billDate,
			String custName, String quatationBillAmount, String discountAmount,
			String totalPaidAmount, String dueAmount) {
		super();
		this.id = id;
		this.billNumber = billNumber;
		this.billDate = billDate;
		this.custName = custName;
		this.quatationBillAmount = quatationBillAmount;
		this.discountAmount = discountAmount;
		this.totalPaidAmount = totalPaidAmount;
		this.dueAmount = dueAmount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getQuatationBillAmount() {
		return quatationBillAmount;
	}
	public void setQuatationBillAmount(String quatationBillAmount) {
		this.quatationBillAmount = quatationBillAmount;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getTotalPaidAmount() {
		return totalPaidAmount;
	}
	public void setTotalPaidAmount(String totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}
	public String getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(String dueAmount) {
		this.dueAmount = dueAmount;
	}
}
