<template>
  <div class="login-block">
    <div align="center">
      <img src="./image/Frame.png" style="margin: 0 auto"/>
    </div>
    <el-divider></el-divider>
    <div class="input-block">
      <el-form :model="form" status-icon label-width="60px" :rules="rules" ref="loginForm">
        <el-form-item label="用户名" prop="name">
          <el-input v-model="form.name" placeholder="请输入用户名" style="width:250px"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" style="width:250px" show-password></el-input>
        </el-form-item>
        <el-form-item :error="form.loginError">
          <el-button type="primary" @click="onSubmit('loginForm')" style="width:165px">登录</el-button>
          <el-button type="danger" @click="register">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script type="module">
module.exports = {
  data() {
    var nameVali = (rule,value,callback) => {
      if(!value){
        return callback(new Error('用户名不能为空'));
      }
      if(value.length>15){
        return callback(new Error('用户名过长（15字符以内）'));
      }
      let regAccount = /[#$^&*\s\\/\"\'+]+/g
      if (regAccount.test(value)) {
        return callback(new Error('用户名包含非法字符'));
      }
      callback();
    };
    var passvali = (rule,value,callback) => {
      if(!value){
        return callback(new Error('密码不能为空'));
      }
      callback();
    };
    return {
      form: {
        name: '',
        password: '',
        loginError: ''
      },
      rules:{
        name:[{ validator:nameVali, trigger: 'blur' }],
        password:[{ validator: passvali, trigger: 'blur' }]
      }
    };
  },
  methods: {
    onSubmit(formname) {
      this.$refs[formname].validate((valid)=>{
        if(valid){
          var _this = this;
          document.cookie
          axios({
            headers:{
              'Content-Type':'application/x-www-form-urlencoded'
            },
            method:'post',
            url:'/session',
            data:"name="+this.$data.form.name+"&"+"password="+this.$data.form.password

          }).then(function (response) {
            if(response.data==true) {
              window.location.href="/";
            }
            else {
              _this.$data.form.loginError="用户名或密码错误!";
            }
          });
        }
      });
    },
    register() {
      this.$router.push({path:'/register'});
    }
  }
}
</script>

<style scoped>
.input-block{
  width: 330px;
  margin: auto;
  position:relative;
  top: 120px;
}
.login-block{
  margin:auto; width:400px; height:450px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.8);
  margin-top: 150px;
  background: rgba(255,255,255,0.8);
  border-radius: 10px;
}
</style>
