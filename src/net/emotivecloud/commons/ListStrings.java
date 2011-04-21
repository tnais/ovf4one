package net.emotivecloud.commons;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ListStrings implements List<String> {
	@XmlElement
	public List<String> list;
	
	public ListStrings() {
		list = new Vector<String>();
	}

	public boolean add(String arg0) {
		return list.add(arg0);
	}

	public void add(int arg0, String arg1) {
		list.add(arg0, arg1);
	}

	public boolean addAll(Collection<? extends String> arg0) {
		return list.addAll(arg0);
	}

	public boolean addAll(int arg0, Collection<? extends String> arg1) {
		return list.addAll(arg0, arg1);
	}

	public void clear() {
		list.clear();
	}

	public boolean contains(Object arg0) {
		return list.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return list.containsAll(arg0);
	}

	public String get(int arg0) {
		return list.get(arg0);
	}

	public int indexOf(Object arg0) {
		return list.indexOf(arg0);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public Iterator<String> iterator() {
		return list.iterator();
	}

	public int lastIndexOf(Object arg0) {
		return list.lastIndexOf(arg0);
	}

	public ListIterator<String> listIterator() {
		return list.listIterator();
	}

	public ListIterator<String> listIterator(int arg0) {
		return list.listIterator(arg0);
	}

	public boolean remove(Object arg0) {
		return list.remove(arg0);
	}

	public String remove(int arg0) {
		return list.remove(arg0);
	}

	public boolean removeAll(Collection<?> arg0) {
		return list.retainAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0) {
		return list.retainAll(arg0);
	}

	public String set(int arg0, String arg1) {
		return list.set(arg0, arg1);
	}

	public int size() {
		return list.size();
	}

	public List<String> subList(int arg0, int arg1) {
		return list.subList(arg0, arg1);
	}
	
	public String toString() {
		return list.toString();
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] arg0) {
		return list.toArray(arg0);
	}
}
