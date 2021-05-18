<template>
  <div style="height: 100%;"
  >
    <div class="upload-block">
      <el-upload
          v-show="!hasUpload"
          :limit="1"
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
      <div v-show="hasUpload" @click="handlePictureCardPreview" style="width: 500px;padding: 30px;margin: auto">
        <el-image
            style="width: 500px;"
            :src="previewUrl"
            fit="contain">
        </el-image>
      </div>
      <el-divider></el-divider>
      <div style="text-align: left;width: 500px;margin: auto">
        <el-tag
            :key="tag"
            v-for="tag in labelArray"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)">
          {{tag}}
        </el-tag>
        <el-input
            maxlength="15"
            class="input-new-tag"
            v-if="inputVisible"
            v-model="inputValue"
            ref="saveTagInput"
            size="small"
            @keyup.enter.native="handleInputConfirm"
            @blur="handleInputConfirm"
        >
        </el-input>
        <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 添加标签</el-button>
      </div>
      <el-form :model="form" label-width="80px" class="form-block">
        <el-form-item label="作品名称">
          <el-input v-model="form.name" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item label="作品类型">
          <el-select v-model="form.group" placeholder="作品类型" style="width: 400px">
            <el-option label="原画" value="1"></el-option>
            <el-option label="分镜稿" value="2"></el-option>
            <el-option label="人设图" value="3"></el-option>
            <el-option label="其他" value="4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input type="textarea" v-model="form.comment" placeholder="写一些介绍吧" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="uploadArtwork">上传</el-button>
        </el-form-item>
      </el-form>
      <el-progress :percentage="progressBar" style="position: relative;bottom: 30px;width: 400px;margin: auto;padding-top: 20px"></el-progress>
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
        comment:'',
        labels: '',
        group:''
      },
      labelArray:[],
      dialogVisible: false,
      uploadUrl:'/artworks',
      hasUpload:false,
      previewUrl:'',
      progressBar:0,
      inputVisible: false,
      inputValue: ''
    };
  },
  methods: {
    handleClose(tag) {
      this.labelArray.splice(this.labelArray.indexOf(tag), 1);
    },

    showInput() {
      if(this.labelArray.length<5){
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      }
      else{
        this.$message({
          message: '最多添加5个标签',
          type: 'warning',
        });
      }
    },

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        if(this.labelArray.findIndex((value)=>value==inputValue)==-1){
          this.labelArray.push(inputValue);
        }
        else {
          this.$message({
            message: '标签已存在',
            type: 'warning',
          });
        }
      }
      this.inputVisible = false;
      this.inputValue = '';
    },
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
      this.form.labels = JSON.stringify(this.labelArray);
      this.$message({
        message: '正在上传'
      });
      this.$refs.upload.submit();
    },
    progressHandler(event, file, fileList){
      this.progressBar = Number((event.loaded / event.total * 100).toFixed(2))
    },
    responseHandler(response){
      if(response=="noSession"||response==false){
        this.$message({
          message: '未登录或文件格式错误，上传失败',
          type: 'error',
        });
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
        message: '服务器出现问题，上传失败',
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
  padding-bottom: 30px;
  margin-bottom: 50px;
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
  margin-top: 30px;
  border: 1px dashed;
  border-color: #3a8ee6;
  border-radius: 15px;
  margin-bottom: 40px;
}
.el-tag + .el-tag {
  margin-left: 10px;
  margin-bottom: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;

}
</style>
