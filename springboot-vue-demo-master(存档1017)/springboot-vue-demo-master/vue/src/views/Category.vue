<template>
  <div style="padding: 10px">
    <!--    功能区域-->
<!--    <div style="margin: 10px 0">-->
<!--      <el-button type="primary" @click="add" v-if="user.role === 1">新增</el-button>-->
<!--    </div>-->

    <!--    搜索区域-->
<!--    <div style="margin: 10px 0">-->
<!--      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>-->
<!--      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>-->
<!--    </div>-->
    <el-table
        v-loading="loading"
        :data="tableData"
        border
        stripe
        style="width: 100%"
        row-key="id"
        default-expand-all
    >
<!--      <el-table-column-->
<!--          prop="id"-->
<!--          label="ID"-->
<!--          sortable-->
<!--      >-->
<!--      </el-table-column>-->
      <el-table-column
          prop="name"
          label="名称">
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="mini" @click="handleEdit(scope.row)" v-if="user.role === 1">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)" v-if="user.role === 1">
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-card style="margin: 10px 0; width: 50%">
      <div class="custom-tree-container">
        <el-tree :data="tableData" :props="defaultProps" show-checkbox
                 @check-change="handleCheckChange"
                 node-key="id"
                 :default-expanded-keys="[1, 2]"
                 :default-checked-keys="checkedList"
        >
          <template #default="{ node, data }">
        <span class="custom-tree-node">
          <span>{{ node.label }}</span>
          <span>
            <a
                @click="remove(node, data)">
              Delete
            </a>
          </span>
        </span>
          </template>
        </el-tree>
      </div>
    </el-card>

    <el-card style="width: 50%">
      <el-cascader :options="options" clearable @change="changeCas" v-model="casdata"></el-cascader>
    </el-card>

    <div style="margin: 10px 0">
      <el-dialog title="提示" v-model="dialogVisible" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="名称">
            <el-input v-model="form.name" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
        </template>
      </el-dialog>

    </div>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  name: 'Category',
  components: {

  },
  data() {
    return {
      user: {},
      loading: true,
      form: {},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      casdata: ['Anhui', 'Hefei', 'Zhengwu'],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      checkedList: [],
      options: []
    }
  },

// {
//   value: 'Anhui',
//       label: '安徽省',
//     children: [
//   {
//     value: 'Hefei',
//     label: '合肥市',
//     children: [
//       {
//         value: 'Zhenwu',
//         label: '政务区',
//       },
//     ],
//   },
//   {
//     value: 'Wuhu',
//     label: '芜湖市',
//     children: [
//       {
//         value: 'Jinghu',
//         label: '镜湖区',
//       },
//     ],
//   }
// ]
// },
// {
//   value: 'Jiangsu',
//       label: '江苏省',
//     children: [
//   {
//     value: 'Nanjing',
//     label: '南京市',
//     children: [
//       {
//         value: 'Xuanwu',
//         label: '玄武区',
//       },
//     ],
//   }
// ]
// }
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
    // 请求服务端，确认当前登录用户的 合法信息
    request.get("/user/" + this.user.id).then(res => {
      if (res.code === '0') {
        this.user = res.data
      }
    })

    request.get("/area/tree").then(res => {
      console.log(res.data)
      this.options = res.data
    })

    this.load()
  },
  methods: {
    changeCas(data) {
      console.log(data)
      console.log(this.casdata)
    },
    remove(node, data) {
      request.delete("/category/" + data.id).then(res => {
        this.load()
      })
      // console.log(data)
      // const parent = node.parent;
      // const children = parent.data.children || parent.data;
      // const index = children.findIndex(d => d.id === data.id);
      // children.splice(index, 1);
      // this.data = [...this.data]
    },
    handleCheckChange(data, checked, indeterminate) {
      console.log(data.id);
      console.log(data.name);
    },
    load() {
      this.loading = true
      request.get("/category").then(res => {
        this.loading = false
        this.tableData = res.data

        // 赋值选中的节点
        let checkedData = [4];
        this.checkedList = checkedData
      })

    },
    add() {
      this.dialogVisible = true
      this.form = {}
    },
    save() {
      if (this.form.id) {  // 更新
        request.put("/category", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      }  else {  // 新增
        request.post("/category", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }

          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      }
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true

    },
    handleDelete(id) {
      console.log(id)
      request.delete("/category/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()  // 删除之后重新加载表格的数据
      })
    },
    handleSizeChange(pageSize) {   // 改变当前每页的个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {  // 改变当前页码触发
      this.currentPage = pageNum
      this.load()
    }
  }
}
</script>
<style>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
