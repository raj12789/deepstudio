package in.deepstudio.framingcentre.domain;
// default package
// Generated Oct 27, 2014 11:32:10 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * FrameThickness generated by hbm2java
 */
@Entity
@Table(name = "frame_thickness",schema="public")
public class FrameThickness implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8044063901528347674L;
	private Long frameThicknessId;
	private String frameThicknessSize;
	private String frameDesc;
	private String frameMeasurementsType;
	private Date createdDate;
	private Date updatedDate;
	private String ipAddress;
	private Set<FrameDetails> frameDetailses = new HashSet<>(0);

	public FrameThickness() {
	}

	public FrameThickness(String frameThicknessSize,
			String frameMeasurementsType, Date createdDate, Date updatedDate,
			String ipAddress) {
		this.frameThicknessSize = frameThicknessSize;
		this.frameMeasurementsType = frameMeasurementsType;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.ipAddress = ipAddress;
	}

	public FrameThickness(String frameThicknessSize, String frameDesc,
			String frameMeasurementsType, Date createdDate, Date updatedDate,
			String ipAddress, Set<FrameDetails> frameDetailses) {
		this.frameThicknessSize = frameThicknessSize;
		this.frameDesc = frameDesc;
		this.frameMeasurementsType = frameMeasurementsType;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.ipAddress = ipAddress;
		this.frameDetailses = frameDetailses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "frame_thickness_id", unique = true, nullable = false)
	public Long getFrameThicknessId() {
		return this.frameThicknessId;
	}

	public void setFrameThicknessId(Long frameThicknessId) {
		this.frameThicknessId = frameThicknessId;
	}

	@Column(name = "frame_thickness_size", nullable = false, length = 10)
	public String getFrameThicknessSize() {
		return this.frameThicknessSize;
	}

	public void setFrameThicknessSize(String frameThicknessSize) {
		this.frameThicknessSize = frameThicknessSize;
	}

	@Column(name = "frame_desc", length = 200)
	public String getFrameDesc() {
		return this.frameDesc;
	}

	public void setFrameDesc(String frameDesc) {
		this.frameDesc = frameDesc;
	}

	@Column(name = "frame_measurements_type", nullable = false, length = 10)
	public String getFrameMeasurementsType() {
		return this.frameMeasurementsType;
	}

	public void setFrameMeasurementsType(String frameMeasurementsType) {
		this.frameMeasurementsType = frameMeasurementsType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date", nullable = false, length = 19)
	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name = "ip_address", nullable = false, length = 25)
	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "frameThickness")
	public Set<FrameDetails> getFrameDetailses() {
		return this.frameDetailses;
	}

	public void setFrameDetailses(Set<FrameDetails> frameDetailses) {
		this.frameDetailses = frameDetailses;
	}

}
