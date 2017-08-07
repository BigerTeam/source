package com.source.app.utils;

import java.util.ArrayList;
import java.util.List;

import com.source.base.model.vo.TreeNode;

public class TreeNodeUtils {
    /**
     * 获取父节点菜单
     *
     * @param trees 所有树菜单集合
     * @return
     */
    public final static List<TreeNode> buildTreeNode(List<TreeNode> trees) {
        List<TreeNode> newTrees = new ArrayList<>();
        for (TreeNode tree : trees) {
            if (tree == null || tree.getPid() == null || tree.getPid()<=0) {//如果pId为空，则该节点为父节点
                //递归获取父节点下的子节点
                tree.setChildren(getChildrenNodes(tree.getId(), trees));
                newTrees.add(tree);
            }
        }
        return newTrees;
    }

    /**
     * 递归获取子节点下的子节点
     *
     * @param pId       父节点的ID
     * @param treesList 所有菜单树集合
     * @return
     */
    private final static List<TreeNode> getChildrenNodes(Long pId, List<TreeNode> treesList) {
        List<TreeNode> newTrees = new ArrayList<>();
        for (TreeNode tree : treesList) {
            if (tree == null || tree.getPid() == null || tree.getPid()<=0) continue;
            if (tree.getPid().equals(pId)) {
                //递归获取子节点下的子节点，即设置树控件中的children
                tree.setChildren(getChildrenNodes(tree.getId(), treesList));
                newTrees.add(tree);
            }
        }
        return newTrees;
    }
}
