<template>
  <div>
    <div class="artwork-block" >
      <el-image
          v-if="artwork"
          style="width: 100%"
          :src="artwork.artwork_location"
          fit="contain"
          :preview-src-list="imageList"
      ></el-image>
    </div>
    <div class="info-block">
      <div style="font-size: 20px;line-height: 1.7;">{{artwork.artwork_name}}</div>
      <div style="color: #8c939d;font-size: 14px;line-height: 1.7;margin-top: 8px">{{artwork.artwork_comment==""?"上传者没有留下评论":artwork.artwork_comment}}</div>
    </div>
    <div class="comment-block">
      <el-form :model="form" class="form-block">
        <el-form-item>
          <div style="text-align: left;">
            <el-col :span="2" style="display: inline-block;height: 64.8px">
              <el-avatar :size="48">
                <el-image :src="user.user_avatar" fit="cover" style="height: 100%" v-if="user"></el-image>
                <i class="el-icon-user-solid" v-if="!user.user_avatar"></i>
              </el-avatar>
            </el-col>
            <el-col :span="20" style="padding-right: 20px">
              <el-input type="textarea" style="font-size: 12px;" v-model="form.comment" :rows="3" placeholder="发表一条评论吧" style="margin: auto;width: 100%"></el-input>
            </el-col>
            <el-col :span="2" style="text-align: right;">
              <el-button type="primary" @click="uploadComment" style="height:64.8px;">发送！</el-button>
            </el-col>
          </div>
        </el-form-item>
      </el-form>
      <div style="display: inline-block;width: 100%">
        <div style="text-align: center;color: #6d757a;" v-if="comments.length==0">
          <el-divider></el-divider>
          <div style="padding: 50px;font-size: 18px">
            还没有人发表过评论
          </div>
        </div>
        <transition-group tag="span" name="comment-list">
          <div v-for="(comment,index) in comments" :key="comment.comment_id" class="a-comment">
            <div class="my-divider"></div>
            <el-col :span="2">
              <el-avatar :size="48">
                <el-image :src="comment.user_avatar" fit="cover" style="height: 100%" v-show="comment.user_avatar"></el-image>
                <i class="el-icon-user-solid" v-show="!comment.user_avatar"></i>
              </el-avatar>
            </el-col>
            <el-col :span="22">
              <div style="vertical-align: center">
                <div style="color: #6d757a;font-size: 12px;"><b>{{comment.user_name}}</b></div>
                <div style="padding-top: 8px">{{comment.comment_content}}</div>
              </div>
              <div style="text-align: left;padding-top: 8px">
                <div class="comment-other" style="cursor: text">{{comment.comment_createTime}}</div>
                <div class="comment-other" @click="reply(index)">回复</div>
              </div>
            </el-col>
          </div>
        </transition-group>
      </div>
    </div>
  </div>
</template>

<script>
module.exports={
  data(){
    return{
      artworkId:this.$route.params.id,
      artwork:'',
      imageList:[],
      form:{
        comment:''
      },
      user:'',
      page:0,
      comments:[]
    }
  },
  methods:{
    reply(index){
      var id = this.comments[index].comment_id;
      alert(id)
    },
    uploadComment(){
      var _this = this;
      axios({
        headers:{
          'Content-Type':'application/x-www-form-urlencoded'
        },
        method:'post',
        url:'/comment/'+this.artworkId,
        data:"content="+this.$data.form.comment

      }).then(response=>{
        if(response.data!="noSession"){
          var comm = response.data;
          comm.user_avatar = _this.user.user_avatar;
          comm.user_id = _this.user.user_id;
          comm.user_name = _this.user.user_name;
          _this.comments.unshift(comm);
        }
      });

    }
  },
  mounted(){
    this.$router.app.colSpan=18;
    var _this = this;
    axios({
      method:'get',
      url:'/artworks/'+this.artworkId,
    }).then(function (response) {
      var data = response.data;
      _this.artwork = data;
      _this.imageList.push(data.artwork_location)
    });
    axios({
      method:'get',
      url:'/comment/'+this.artworkId+'/timeorder/'+this.page,
    }).then(function (response) {
      var data = response.data;
      for(var i in data){
        if(data[i].comment_toId==0){
          _this.comments.push(data[i]);
        }
      }

    });
    this.user = this.$router.app.user;
    console.log(this.user)
    this.$root.$on('user',(a)=>{
      _this.user = '';
      _this.user = a;
      console.log("dfdf")
      console.log(_this.user);
    });
  },
  destroyed(){
    this.$router.app.colSpan=24;
  }

}
</script>

<style scoped>
.artwork-block{
  height: auto;
  background: #FFFFFF;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin-right: 30px;
  margin-bottom: 18px;
  margin-left: 30px;
  margin-top: 0;
}
.comment-block{
  padding: 30px;
  margin: 30px;
  height: auto;
  background: #FFFFFF;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin-bottom: 50px;
  margin-top: 0;
}
.info-block{
  padding: 10px 30px 10px 30px;
  margin: 30px;
  height: auto;
  background: #FFFFFF;
  text-align: left;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin-bottom: 18px;
  margin-top: 0;
}
.form-block{
  width: 100%;
}
.a-comment{
  text-align: left;
  display: inline-block;
  width: 100%;
}
.my-divider{
  border-bottom: 1px solid #ddd;
  height: 1px;
  margin: 0px 0px 12px 0px;
}
.comment-list-enter-active .comment-list-leave-active{
  position: absolute;
}
.comment-list-enter .comment-list-leave-to
  /* .list-leave-active for below version 2.1.8 */ {
  opacity: 0;
  transform: scale(0.2,0.2);
  transform: translateY(-30px);
}
.comment-list-move{
  transition: transform 1s;
}
.comment-other{
  display:inline-block;font-size: 12px;color: #909399;cursor: pointer;text-align: center;
  margin-right: 8px;
}
</style>
