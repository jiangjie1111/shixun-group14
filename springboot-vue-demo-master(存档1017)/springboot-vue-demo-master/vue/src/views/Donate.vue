<template>
  <div style="padding: 10px">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <div style="padding: 10px 0; font-size: 18px">辛苦了~ 请作者喝杯咖啡~</div>
          <el-image style="width: 240px" :src="wx" :preview-src-list="[wx]"></el-image>
          <el-image style="width: 240px; margin-left: 100px" :src="alipay" :preview-src-list="[alipay]"></el-image>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Donate",
  data() {
    return {
      wx: "https://img-blog.csdnimg.cn/9d71c53c26a5463ba283b46a91a325de.png",
      alipay: 'https://img-blog.csdnimg.cn/c5bcaebd10544ed7ac86654a9a2bc9d5.jpg'
    }
  }
}
</script>

<style scoped>

</style>
