package in.deepstudio.framingcentre.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "frame_cust_billing_info_detail",schema="public")
public class FrameCustBillingInfoDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3097027942834217444L;
	private Long billDetailId;
	private FrameCustBillingInfo frameCustBillingInfo;
	private FrameDetails frameDetails;
	private FrameClientType frameClientType;
	private String concernDesciption;
	private Integer quantity;
	private Integer actualRate;
	private Integer sellingRate;
	
	
	public FrameCustBillingInfoDetail() {
		
	}

	public FrameCustBillingInfoDetail(Long billDetailId,
			FrameCustBillingInfo frameCustBillingInfo,
			FrameDetails frameDetails, FrameClientType frameClientType,String concernDesciption,
			Integer quantity, Integer actualRate, Integer sellingRate) {
		super();
		this.billDetailId = billDetailId;
		this.frameCustBillingInfo = frameCustBillingInfo;
		this.frameDetails = frameDetails;
		this.frameClientType = frameClientType;
		this.concernDesciption=concernDesciption;
		this.quantity = quantity;
		this.actualRate = actualRate;
		this.sellingRate = sellingRate;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bill_detail_id",nullable=false,unique=true)
	public Long getBillDetailId() {
		return billDetailId;
	}
	public void setBillDetailId(Long billDetailId) {
		this.billDetailId = billDetailId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cust_billing_id", nullable = false)
	public FrameCustBillingInfo getFrameCustBillingInfo() {
		return frameCustBillingInfo;
	}
	public void setFrameCustBillingInfo(FrameCustBillingInfo frameCustBillingInfo) {
		this.frameCustBillingInfo = frameCustBillingInfo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "frame_qualified_id", nullable = false)
	public FrameDetails getFrameDetails() {
		return frameDetails;
	}
	public void setFrameDetails(FrameDetails frameDetails) {
		this.frameDetails = frameDetails;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_type_id", nullable = false)
	public FrameClientType getFrameClientType() {
		return frameClientType;
	}
	public void setFrameClientType(FrameClientType frameClientType) {
		this.frameClientType = frameClientType;
	}
	
	@Column(name="concern_desciption",nullable=false,length=200)
	public String getConcernDesciption() {
		return concernDesciption;
	}
	public void setConcernDesciption(String concernDesciption) {
		this.concernDesciption = concernDesciption;
	}
	
	@Column(name="quantity",nullable=false)
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Column(name="actual_rate",nullable=false)
	public Integer getActualRate() {
		return actualRate;
	}
	public void setActualRate(Integer actualRate) {
		this.actualRate = actualRate;
	}
	
	@Column(name="selling_rate",nullable=false)
	public Integer getSellingRate() {
		return sellingRate;
	}
	public void setSellingRate(Integer sellingRate) {
		this.sellingRate = sellingRate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FrameCustBillingInfoDetail [billDetailId=");
		builder.append(billDetailId);
		builder.append(", frameCustBillingInfo=");
		builder.append(frameCustBillingInfo);
		builder.append(", frameDetails=");
		builder.append(frameDetails);
		builder.append(", frameClientType=");
		builder.append(frameClientType);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", actualRate=");
		builder.append(actualRate);
		builder.append(", sellingRate=");
		builder.append(sellingRate);
		builder.append("]");
		return builder.toString();
	}
	
	
}
