/*
 * Copyright (c) 2024 LangChat. TyCoding All Rights Reserved.
 *
 * Licensed under the GNU Affero General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.gnu.org/licenses/agpl-3.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.tycoding.langchat.upms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.tycoding.langchat.common.core.exception.ServiceException;
import cn.tycoding.langchat.upms.entity.SysDept;
import cn.tycoding.langchat.upms.mapper.SysDeptMapper;
import cn.tycoding.langchat.upms.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门表(Dept)表服务实现类
 *
 * @author tycoding
 * @since 2024/4/15
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    /**
     * 查询部门列表
     *
     * @param sysDept 查询条件对象
     * @return 部门列表
     */
    @Override
    public List<SysDept> list(SysDept sysDept) {
        // 按照部门排序号升序查询部门列表
        return baseMapper.selectList(new LambdaQueryWrapper<SysDept>()
                .orderByAsc(SysDept::getOrderNo));
    }

    /**
     * 构建部门树形结构
     *
     * @param sysDept 查询条件对象
     * @return 部门树形结构列表
     */
    @Override
    public List<Tree<Object>> tree(SysDept sysDept) {
        // 查询部门列表，排除指定ID的部门
        List<SysDept> sysDeptList = baseMapper.selectList(new LambdaQueryWrapper<SysDept>()
                .ne(sysDept.getId() != null, SysDept::getId, sysDept.getId()));

        // 构建树形结构
        List<TreeNode<Object>> nodeList = CollUtil.newArrayList();
        // 遍历部门列表，将每个部门转换为树节点
        sysDeptList.forEach(t -> {
            TreeNode<Object> node = new TreeNode<>(
                    t.getId(),
                    t.getParentId(),
                    t.getName(),
                    0
            );
            // 设置节点的额外信息，如排序号和描述
            node.setExtra(Dict.create().set("orderNo", t.getOrderNo()).set("des", t.getDes()));
            nodeList.add(node);
        });
        // 使用TreeUtil工具类构建树形结构，根节点ID为"0"
        return TreeUtil.build(nodeList, "0");
    }

    /**
     * 删除部门
     *
     * @param id 部门ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        // 查询该部门下的子部门列表
        List<SysDept> list = baseMapper.selectList(new LambdaQueryWrapper<SysDept>().eq(SysDept::getParentId, id));
        // 如果存在子部门，则抛出异常，不允许删除
        if (!list.isEmpty()) {
            throw new ServiceException("该部门包含子节点，不能删除");
        }
        // 删除指定ID的部门
        baseMapper.deleteById(id);
    }
}
