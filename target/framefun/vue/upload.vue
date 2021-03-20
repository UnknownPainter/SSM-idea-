<template>
  <div style="height: 100%;"
  >
    <div class="upload-block">
      <el-upload
          class="a-el-upload"
          drag
          ref="upload"
          :action="uploadUrl"
          :auto-upload="false"
          :show-file-list="false"
          list-type="picture"
          :data="form"
          :on-change="showPreview"
          :on-progress="progressHandler"
          :on-success="responseHandler"
          :on-error="errorHandler"
      >
        <i class="el-icon-upload"><div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div></i>
      </el-upload>
      <div v-show="hasUpload" @click="handlePictureCardPreview" style="width: 100%">
        <el-image
            style="width: 100px; height: 100px"
            :src="previewUrl"
            fit="contain">
        </el-image>
      </div>
      <el-divider></el-divider>

      <el-form :model="form" label-width="80px" class="form-block">
        <el-form-item label="作品名称">
          <el-input v-model="form.name" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input type="textarea" v-model="form.comment" placeholder="写一些介绍吧" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="uploadArtwork">上传</el-button>
        </el-form-item>
      </el-form>
      <el-progress :percentage="progressBar" style="position: relative;bottom: 30px;width: 400px;margin: auto"></el-progress>
      <div></div>

      <el-dialog :visible.sync="dialogVisible" :modal-append-to-body="false">
        <img width="100%" :src="previewUrl" alt="">
      </el-dialog>
    </div>
  </div>
</template>

<script type="module">
module.exports={
  data() {
    return {
      form:{
        name:'',
        comment:''
      },
      dialogVisible: false,
      uploadUrl:'/artworks',
      hasUpload:false,
      previewUrl:'',
      progressBar:0
    };
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview() {
      this.dialogVisible = true;
    },
    showPreview(file) {
      this.previewUrl = file.url;
      this.hasUpload = true;
    }
    ,
    uploadArtwork(){
      this.$message({
        message: '正在上传'
      });
      this.$refs.upload.submit();
    },
    progressHandler(event, file, fileList){
      this.progressBar = Number((event.loaded / event.total * 100).toFixed(2))
    },
    responseHandler(response){
      if(response=="noSession"){
        console.log("get");
      }
      else{
        this.$message({
          message: '上传成功',
          type: 'success',
        });
      }
    },
    errorHandler(){
      this.$message({
        showClose: true,
        message: '上传失败',
        type: 'error',
        duration:'5000'
      });
    }
  },
  mounted(){
    this.$router.app.loadingMain=false;

  }

}
</script>

<style scoped>
.upload-block{
  height: auto;
  margin: auto;
  width: 70%;
  min-height: 100%;
  background: #FFFFFF;
  text-align: center;
  box-shadow: 0 1px 3px rgba(18,18,18,.1);
  border-radius: 15px;
}
.a-el-upload{
  top: 20px;
  text-align: center;
  margin: auto;
  padding-bottom: 20px;
  position: relative;
}
.form-block{
  padding-top: 20px;
  width: 500px;
  margin: auto;
  margin-top: 100px;
  border: 1px dashed;
  border-color: #3a8ee6;
  border-radius: 15px;
  margin-bottom: 40px;

}
</style>
