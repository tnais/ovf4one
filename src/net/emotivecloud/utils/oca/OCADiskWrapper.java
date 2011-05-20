package net.emotivecloud.utils.oca;

/**
 * Class  <code>OCADiskWrapper</code>  wrapper  for part  of  the  XML
 * returned by OCA info() method.
 *
 * @author <a href="mailto:saint@eng.it">Gian Uberto Lauri</a>
 * @version $Revision$
 */
public class OCADiskWrapper {

	/**
	 * Describe readonly here.
	 */
	private String readonly;

	/**
	 * Describe source here.
	 */
	private String source;

	/**
	 * Describe tgarget here.
	 */
	private String tgarget;

	/**
	 * Describe size here.
	 */
	private long size;

	/**
	 * Describe type here.
	 */
	private String type;

	/**
	 * Describe diskId here.
	 */
	private String diskId;

	/**
	 * Get the <code>Readonly</code> value.
	 *
	 * @return a <code>String</code> 
	 */
	public final String getReadonly() {
		return readonly;
	}

	/**
	 * Set the <code>Readonly</code> value.
	 *
	 * @param newReadonly The new Readonly value.
	 */
	public final void setReadonly(final String newReadonly) {
		this.readonly = newReadonly;
	}

	/**
	 * Get the <code>Source</code> value.
	 *
	 * @return a <code>String</code> 
	 */
	public final String getSource() {
		return source;
	}

	/**
	 * Set the <code>Source</code> value.
	 *
	 * @param newSource The new Source value.
	 */
	public final void setSource(final String newSource) {
		this.source = newSource;
	}

	/**
	 * Get the <code>Tgarget</code> value.
	 *
	 * @return a <code>String</code> 
	 */
	public final String getTgarget() {
		return tgarget;
	}

	/**
	 * Set the <code>Tgarget</code> value.
	 *
	 * @param newTgarget The new Tgarget value.
	 */
	public final void setTgarget(final String newTgarget) {
		this.tgarget = newTgarget;
	}

	/**
	 * Get the <code>Size</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final long getSize() {
		return size;
	}

	/**
	 * Set the <code>Size</code> value.
	 *
	 * @param newSize The new Size value.
	 */
	public final void setSize(final long newSize) {
		this.size = newSize;
	}

	/**
	 * Get the <code>Type</code> value.
	 *
	 * @return a <code>String</code> 
	 */
	public final String getType() {
		return type;
	}

	/**
	 * Set the <code>Type</code> value.
	 *
	 * @param newType The new Type value.
	 */
	public final void setType(final String newType) {
		this.type = newType;
	}

	/**
	 * Get the <code>DiskId</code> value.
	 *
	 * @return a <code>String</code> 
	 */
	public final String getDiskId() {
		return diskId;
	}

	/**
	 * Set the <code>DiskId</code> value.
	 *
	 * @param newDiskId The new DiskId value.
	 */
	public final void setDiskId(final String newDiskId) {
		this.diskId = newDiskId;
	}
}
