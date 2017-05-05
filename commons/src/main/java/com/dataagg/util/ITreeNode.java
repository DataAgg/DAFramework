package com.dataagg.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ITreeNode<C extends ITreeNode<?>> extends Serializable {
	public static final Logger LOG = LoggerFactory.getLogger(ITreeNode.class);

	public Long getId();

	public void setId(Long id);

	public Long getParentId();

	public void setParentId(Long parentId);

	public String getParentIds();

	public void setParentIds(String parentIds);

	public String getName();

	public void setName(String name);

	public String getCode();

	public void setCode(String code);

	public List<C> getItems();

	public void setItems(List<C> items);

	public default void addChild(C node) {
		List<C> items = getItems();
		if (items == null) {
			items = new ArrayList<>();
		}
		node.setParentId(getId());
		items.add(node);
		setItems(items);
	}

	@SuppressWarnings("unchecked")
	public default ITreeNode<C> buildTree(List<ITreeNode<C>> nodes) {
		Map<Long, ITreeNode<C>> allNodes = new Hashtable<>();
		for (ITreeNode<C> node : nodes) {
			allNodes.put(node.getId(), node);
		}

		ITreeNode<C> root = null;
		for (ITreeNode<C> node : nodes) {
			ITreeNode<C> parent = null;
			if (node.getParentId() != null) {
				parent = allNodes.get(node.getParentId());
				if (parent != null) {
					parent.addChild((C) node);
				} else {
					LOG.debug("不能找到根节点" + node.getId() + "--" + node.getParentId());
				}
			} else {
				if (root != null) {
					LOG.debug("重复的根节点" + root.getId() + "--" + node.getParentId());
				}
				root = node;
			}
		}
		return root;
	}
}
