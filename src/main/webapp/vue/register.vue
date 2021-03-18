<template>
  <div class="register-block">
    <div align="center">
      <img src="./image/Frame.png" style="margin: 0 auto"/>
    </div>
    <el-divider></el-divider>
    <div class="input-block">
      <el-form :model="form" status-icon label-width="60px" :rules="rules" ref="loginForm">
        <el-form-item  label="用户名" prop="name">
          <el-input v-model="form.name" placeholder="请输入用户名" style="width:250px"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" style="width:250px" show-password></el-input>
        </el-form-item>
        <el-form-item :error="form.loginError">
          <el-button type="danger" @click="register('loginForm')" style="width: 165px">注册</el-button>
          <el-button type="primary" @click="onSubmit">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script >
module.exports = {
  data() {
    var nameVali = (rule,value,callback) => {
      if(!value){
        return callback(new Error("用户名不能为空"));
      }
      else{
        axios({
          headers:{
            'Content-Type':'application/x-www-form-urlencoded'
          },
          method:'get',
          url:"/user/"+this.$data.form.name
        }).then( (response)=> {
          if(response.data!=true) {
            callback(new Error("用户名已存在"));
          }
          else{
            callback();
          }
        });
      }
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
        loginError: '',
        nameError: ''
      },
      rules:{
        name:[{ validator:nameVali, trigger: 'blur' }],
        password:[{ validator: passvali, trigger: 'blur' }]
      }
    };
  },
  methods: {
    register(formname) {
      this.$refs[formname].validate((valid)=>{
        if(valid){
          var _this = this;
          axios({
            headers:{
              'Content-Type':'application/x-www-form-urlencoded'
            },
            method:'post',
            url:'/user',
            data:"name="+this.$data.form.name+"&"+"password="+this.$data.form.password

          }).then(function (response) {
            if(response.data==true) {
              window.location.href="/home.html";
            }
            else {
              _this.$data.form.loginError="注册失败!";
            }
          });
        }
      });
    },
    onSubmit() {
      this.$router.push({path:'/login'});
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
.register-block{
  margin:auto; width:400px; height:450px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.8);
  margin-top: 150px;
  background: rgba(255,255,255,0.9);
  border-radius: 10px;
}
</style>
