package org.self.tree.node;

/**
 * 双亲存储结构
 * <p>
 * 查询当前节点的双亲比较容易，查询子节点要遍历整棵树
 * 
 * @author TungWang
 */
public class ParentNode<K, V> implements Node<K, V> {

	private K key;
	private V value;
	// 指向双亲的引用
	protected ParentNode<K, V> parent;

	public ParentNode() {
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public ParentNode<K, V> getParent() {
		return parent;
	}

	public void setParent(ParentNode<K, V> parent) {
		this.parent = parent;
	}

}
