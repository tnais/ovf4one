package net.emotivecloud.utils.oca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Class <code>OCAComputeListWrapper</code> models the OCA
 * VirtualMachinePool.info() reply, whose elements are of type type is
 * VMCOMPLEX, modeled by OCACOmplexComputeWrapper. Since the original
 * reply is a list of VMCOMPLEX, this class behaves like a
 * List<OCAComplexComputeWrapper>.
 *
 * For the description of the method, see java.util.List javadoc.
 *
 * @author <a href="mailto:saint@eng.it">Gian Uberto Lauri</a>
 * @version $Revision$
 */
public class OCAComputeListWrapper implements List<OCAComplexComputeWrapper> {

	List<OCAComplexComputeWrapper> complexComputes ;

	public OCAComputeListWrapper() {
		complexComputes = new ArrayList<OCAComplexComputeWrapper>();
	}
	
	public OCAComputeListWrapper(int size) {
		complexComputes = new ArrayList<OCAComplexComputeWrapper>(size);
	}
	
	public OCAComputeListWrapper(List<OCAComplexComputeWrapper> complexComputes) {
		this.complexComputes = complexComputes;
	}


	// Overrides 
	@Override
	public int size() {
		return complexComputes.size();
	}

	@Override
	public boolean isEmpty() {
		return complexComputes.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return complexComputes.contains(o);
	}

	@Override
	public Iterator<OCAComplexComputeWrapper> iterator() {
		return complexComputes.iterator();
	}

	@Override
	public Object[] toArray() {
		return complexComputes.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return complexComputes.toArray(a);
	}

	@Override
	public boolean add(OCAComplexComputeWrapper e) {
		return complexComputes.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return complexComputes.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return complexComputes.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends OCAComplexComputeWrapper> c) {
		return complexComputes.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends OCAComplexComputeWrapper> c) {
		return complexComputes.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return complexComputes.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return complexComputes.retainAll(c);
	}

	@Override
	public void clear() {
		complexComputes.clear();
	}

	@Override
	public OCAComplexComputeWrapper get(int index) {
		return complexComputes.get(index);
	}

	@Override
	public OCAComplexComputeWrapper set(int index, OCAComplexComputeWrapper element) {
		return complexComputes.set(index, element);
	}

	@Override
	public void add(int index, OCAComplexComputeWrapper element) {
		complexComputes.add(index, element);
	}

	@Override
	public OCAComplexComputeWrapper remove(int index) {
		return complexComputes.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return complexComputes.indexOf( o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return complexComputes.lastIndexOf(o);
	}

	@Override
	public ListIterator<OCAComplexComputeWrapper> listIterator() {
		return complexComputes.listIterator();
	}

	@Override
	public ListIterator<OCAComplexComputeWrapper> listIterator(int index) {
		return complexComputes.listIterator(index);
	}

	@Override
	public List<OCAComplexComputeWrapper> subList(int fromIndex, int Index) {
		return complexComputes.subList(fromIndex, Index);
	}

}
