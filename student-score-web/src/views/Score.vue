<template>
  <div class="score-container">
    <el-card class="search-card" shadow="hover">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="学期">
          <el-input v-model="searchForm.semester" placeholder="如：2023-2024-1" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon>搜索
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>重置
          </el-button>
          <el-button type="success" @click="handleAdd">
            <el-icon><Plus /></el-icon>录入成绩
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="table-card" shadow="hover">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column type="index" label="序号" width="60" align="center" />
        <el-table-column prop="studentId" label="学生ID" width="100" />
        <el-table-column prop="courseId" label="课程ID" width="100" />
        <el-table-column prop="score" label="成绩" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getScoreType(row.score)" size="large">{{ row.score }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="examType" label="考试类型" width="100" />
        <el-table-column prop="examDate" label="考试日期" width="120" />
        <el-table-column prop="semester" label="学期" width="120" />
        <el-table-column prop="remark" label="备注" min-width="150" show-overflow-tooltip />
        <el-table-column label="操作" width="200" fixed="right" align="center">
          <template #default="{ row }">
            <el-button type="primary" size="small" text @click="handleEdit(row)">
              <el-icon><Edit /></el-icon>编辑
            </el-button>
            <el-button type="danger" size="small" text @click="handleDelete(row)">
              <el-icon><Delete /></el-icon>删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :page-sizes="[10, 20, 50, 100]"
        :total="pagination.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        class="pagination"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="学生ID" prop="studentId">
          <el-input-number v-model="form.studentId" :min="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="课程ID" prop="courseId">
          <el-input-number v-model="form.courseId" :min="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="成绩" prop="score">
          <el-input-number v-model="form.score" :min="0" :max="100" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="考试类型" prop="examType">
          <el-select v-model="form.examType" placeholder="请选择考试类型" style="width: 100%">
            <el-option label="平时" value="平时" />
            <el-option label="期中" value="期中" />
            <el-option label="期末" value="期末" />
          </el-select>
        </el-form-item>
        <el-form-item label="考试日期" prop="examDate">
          <el-date-picker
            v-model="form.examDate"
            type="date"
            placeholder="请选择考试日期"
            style="width: 100%"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="学期" prop="semester">
          <el-input v-model="form.semester" placeholder="如：2023-2024-1" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitLoading">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getScoreList, addScore, updateScore, deleteScore } from '../api/score'
import { useUserStore } from '../stores/user'

const userStore = useUserStore()
const loading = ref(false)
const submitLoading = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref('录入成绩')
const isEdit = ref(false)
const formRef = ref(null)

const searchForm = reactive({ semester: '' })
const pagination = reactive({ current: 1, size: 10, total: 0 })
const tableData = ref([])

const form = reactive({
  id: null,
  studentId: null,
  courseId: null,
  score: 0,
  examType: '期末',
  examDate: '',
  semester: '',
  remark: ''
})

const rules = {
  studentId: [{ required: true, message: '请输入学生ID', trigger: 'blur' }],
  courseId: [{ required: true, message: '请输入课程ID', trigger: 'blur' }],
  score: [{ required: true, message: '请输入成绩', trigger: 'blur' }],
  examType: [{ required: true, message: '请选择考试类型', trigger: 'change' }],
  semester: [{ required: true, message: '请输入学期', trigger: 'blur' }]
}

const getScoreType = (score) => {
  if (score >= 90) return 'success'
  if (score >= 80) return 'primary'
  if (score >= 70) return 'warning'
  if (score >= 60) return 'info'
  return 'danger'
}

const getList = async () => {
  loading.value = true
  try {
    const res = await getScoreList({
      current: pagination.current,
      size: pagination.size,
      semester: searchForm.semester
    })
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) {
    ElMessage.error('获取成绩列表失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pagination.current = 1
  getList()
}

const handleReset = () => {
  searchForm.semester = ''
  pagination.current = 1
  getList()
}

const handleAdd = () => {
  dialogTitle.value = '录入成绩'
  isEdit.value = false
  Object.assign(form, {
    id: null,
    studentId: null,
    courseId: null,
    score: 0,
    examType: '期末',
    examDate: '',
    semester: '',
    remark: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑成绩'
  isEdit.value = true
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该成绩吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteScore(row.id)
      ElMessage.success('删除成功')
      getList()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  })
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitLoading.value = true
      try {
        if (isEdit.value) {
          await updateScore(form, {
            operatorId: userStore.userInfo.id,
            operatorName: userStore.userInfo.realName,
            reason: '成绩修改'
          })
          ElMessage.success('修改成功')
        } else {
          await addScore(form)
          ElMessage.success('录入成功')
        }
        dialogVisible.value = false
        getList()
      } catch (error) {
        ElMessage.error(isEdit.value ? '修改失败' : '录入失败')
      } finally {
        submitLoading.value = false
      }
    }
  })
}

const handleSizeChange = (size) => {
  pagination.size = size
  getList()
}

const handleCurrentChange = (current) => {
  pagination.current = current
  getList()
}

onMounted(() => {
  getList()
})
</script>

<style scoped>
.score-container {
  padding: 0;
}

.search-card {
  margin-bottom: 20px;
  border-radius: 12px;
}

.table-card {
  border-radius: 12px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
