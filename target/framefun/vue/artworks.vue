<template>
  <div style="text-align: left;width: 90%">
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
      <div style="color: #8c939d;font-size: 12px;margin-top:8px">
        <div style="display: inline-block">{{artwork.artwork_createTime}}</div>
      </div>
      <div style="color: #8c939d;font-size: 14px;line-height: 1.7;margin-top: 8px">{{artwork.artwork_comment==""?"上传者没有留下评论":artwork.artwork_comment}}</div>
      <div>
        <el-tag
            style="margin: 8px 8px 0 0;cursor: pointer"
            @click="tagSearch(tag)"
            :closable="artwork.artwork_artistId==user.user_id"
            v-for="tag in tags"
            :key="tag"
            :type="tagType[Math.round(Math.random()*4)]"
            @close="handleClose(tag)">
          {{tag}}
        </el-tag>
        <el-input
            maxlength="15"
            style="width: 90px"
            v-if="inputVisible"
            v-model="inputValue"
            ref="saveTagInput"
            size="small"
            @keyup.enter.native="handleInputConfirm"
            @blur="handleInputConfirm"
        >
        </el-input>
        <el-button v-if="(!inputVisible)&&(artwork.artwork_artistId==user.user_id)"  size="small" @click="showInput">+ 添加标签</el-button>
      </div>
    </div>
    <div class="comment-block">
      <el-form :model="form" class="form-block">
        <el-form-item>
          <div style="text-align: left">
            <div  style="display: inline-block;height: 64.8px;float: left">
              <el-avatar :size="48">
                <el-image :src="user.user_avatar" fit="cover" style="height: 100%" v-if="user.user_avatar"></el-image>
                <i class="el-icon-user-solid" v-if="!user.user_avatar"></i>
              </el-avatar>
            </div>
            <div style="margin-left:58px;">
              <div style="float: right;display: inline-block;width: 84px;margin-left: -84px" >
                <el-button type="primary" @click="uploadComment" style="height:64.8px;">发送！</el-button>
              </div>
              <div style="margin-right: 94px">
                <el-input type="textarea" style="font-size: 12px;" v-model="form.comment" :rows="3" placeholder="发表一条评论吧" style="margin: auto;width: 100%"></el-input>
              </div>
            </div>

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
            <div @click="goToUser(comment.user_id)" style="display: inline-block;height: 64.8px;float: left;cursor: pointer">
              <el-avatar :size="48">
                <el-image :src="comment.user_avatar" fit="cover" style="height: 100%" v-show="comment.user_avatar"></el-image>
                <i class="el-icon-user-solid" v-show="!comment.user_avatar"></i>
              </el-avatar>
            </div>
              <div style="margin-left: 58px">
                <div style="vertical-align: center">
                  <div @click="goToUser(comment.user_id  )" style="cursor: pointer;color: #6d757a;font-size: 12px;"><b>{{comment.user_name}}</b></div>
                  <div style="padding-top: 8px">{{comment.comment_content}}</div>
                </div>
                <div style="text-align: left;padding-top: 8px;padding-bottom: 8px">
                  <div class="comment-other" style="cursor: text">{{comment.comment_createTime}}</div>
                  <div class="comment-other" @click="reply(index)">回复</div>
                  <div class="comment-other" @click="popReply(index)">
                    查看更多({{comment.comment_replyCount}})
                  </div>
                </div>
                <transition-group tag="span" name="comment-list">
                  <el-form :model="form" class="form-block" v-if="comment.isReplying" key="form">
                    <el-form-item>
                      <div style="text-align: left">
                        <div  style="display: inline-block;height: 64.8px;float: left">
                          <el-avatar :size="48">
                            <el-image :src="user.user_avatar" fit="cover" style="height: 100%" v-if="user.user_avatar"></el-image>
                            <i class="el-icon-user-solid" v-if="!user.user_avatar"></i>
                          </el-avatar>
                        </div>
                        <div style="margin-left:58px;">
                          <div style="float: right;display: inline-block;width: 84px;margin-left: -84px" >
                            <el-button type="primary" @click="uploadReply(index)" style="height:64.8px;">发送！</el-button>
                          </div>
                          <div style="margin-right: 94px">
                            <el-input type="textarea" style="font-size: 12px;" v-model="form.reply" :rows="3" placeholder="发表一条评论吧" style="margin: auto;width: 100%"></el-input>
                          </div>
                        </div>
                      </div>
                    </el-form-item>
                  </el-form>
                  <div v-for="(Areply,index2) in comment.reply" :key="'reply'+Areply.comment_id" class="a-comment">
                    <div @click="goToUser(Areply.user_id)" style="cursor: pointer;display: inline-block;height: 64.8px;float: left;padding: 8px 0 8px 0">
                      <el-avatar :size="24">
                        <el-image :src="Areply.user_avatar" fit="cover" style="height: 100%" v-show="Areply.user_avatar"></el-image>
                        <i class="el-icon-user-solid" v-show="!Areply.user_avatar"></i>
                      </el-avatar>
                    </div>
                    <div style="margin-left: 34px">
                      <div style="vertical-align: center">
                        <div @click="goToUser(Areply.user_id)" style="cursor: pointer;color: #6d757a;font-size: 12px;"><b>{{Areply.user_name}}</b></div>
                        <div style="padding-top: 8px">{{Areply.comment_content}}</div>
                      </div>
                      <div style="text-align: left;padding-top: 8px">
                        <div class="comment-other" style="cursor: text">{{Areply.comment_createTime}}</div>
                      </div>
                    </div>
                  </div>
                </transition-group>
              </div>
          </div>
          <el-pagination
              v-if="show"
              key="page"
              :page-count="pageCount"
              class="page"
              background
              layout="prev, pager, next"
              @current-change="pageChange"
              :current-page="page"
          >
          </el-pagination>
        </transition-group>
      </div>
    </div>
    <el-dialog title="评论回复" :visible.sync="showReply" ref="myDialog">
      <div v-for="(aReply,index) in moreReply" class="a-comment" :key="'more-'+index">
        <div class="my-divider"></div>
        <div @click="goToUser(aReply.user_id)" style="cursor: pointer;display: inline-block;height: 64.8px;float: left;padding: 8px 0 8px 0">
          <el-avatar :size="48">
            <el-image :src="aReply.user_avatar" fit="cover" style="height: 100%" v-show="aReply.user_avatar"></el-image>
            <i class="el-icon-user-solid" v-show="!aReply.user_avatar"></i>
          </el-avatar>
        </div>
        <div style="margin-left: 60px">
          <div style="vertical-align: center">
            <div @click="goToUser(aReply.user_id)" style="cursor: pointer;color: #6d757a;font-size: 12px;"><b>{{aReply.user_name}}</b></div>
            <div style="padding-top: 8px">{{aReply.comment_content}}</div>
          </div>
          <div style="text-align: left;padding-top: 8px">
            <div class="comment-other" style="cursor: text">{{aReply.comment_createTime}}</div>
          </div>
        </div>
      </div>
      <el-pagination
        key="page2"
        :page-count="nowCount"
        class="page"
        background
        layout="prev, pager, next"
        @current-change="replyChange"
      >
      </el-pagination>
    </el-dialog>
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
        comment:'',
        reply:''
      },
      user:'',
      page:0,
      comments:[],
      tagType:['' ,'success'  ,'info', 'warning' ,'danger' ],
      tags:[],
      inputVisible: false,
      inputValue: '',
      pageCount:0,
      show:false,
      showReply:false,
      moreReply:[],
      nowCount:'',
      nowComment:''
    }
  },
  methods:{
    replyChange(e){

      var _this = this;
      axios({
        method:'get',
        url:'/comment/child/'+this.nowComment+'/'+(e-1),
      }).then(function (response) {
        _this.moreReply = response.data;
        _this.$refs.myDialog.$el.scrollTop=0
      });
    },
    popReply(e){
      var _this = this;
      this.nowComment=this.comments[e].comment_id
      this.nowCount=Math.floor((this.comments[e].comment_replyCount+19)/20);
      if(this.nowCount==0)this.nowCount=1;
      axios({
        method:'get',
        url:'/comment/child/'+this.comments[e].comment_id+'/0',
      }).then(function (response) {
        _this.moreReply = response.data;
        _this.showReply=true;

      });
    },
    goToUser(e){
      this.$router.push({path:`/artist/${e}`});
    },
    pageChange(e){
      var _this = this;
      axios({
        method: 'get',
        url: '/comment/'+this.artworkId+'/timeorder/'+(e-1)
      }).then(function (response) {
        _this.comments = [];
        var data = response.data;
        for(var i in data){
          if(data[i].comment_toId==0){
            _this.comments.push(data[i]);
            _this.comments[i].reply=[1];
            _this.comments[i].reply.splice(0,1);
          }
          else{
            for(var j in _this.comments){
              if(_this.comments[j].comment_id==data[i].comment_toId){
                _this.comments[j].reply.push(data[i]);
                break;
              }
            }
          }
        }
      });
    },
    tagSearch(tag){
      this.$router.push({path:`/tag-search/${tag}/0`});
    },
    handleClose(tag) {
      var _this = this;
      axios({
        headers:{
          'Content-Type':'application/x-www-form-urlencoded'
        },
        method:'delete',
        url:'/tag/'+this.artwork.artwork_id+'/'+tag
      }).then(response=>{
        if(response.data!="noSession"){
          if(response.data==true){
            _this.tags.splice(this.tags.indexOf(tag), 1);
            _this.$message({
              message: '标签删除成功',
              type: 'success',
            });
          }
        }
      });
    },
    showInput() {
      if(this.tags.length<5){
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
      var _this = this;
      let inputValue = this.inputValue;
      if (inputValue) {
        if(this.tags.findIndex((value)=>value==inputValue)==-1){
          axios({
            headers:{
              'Content-Type':'application/x-www-form-urlencoded'
            },
            method:'post',
            url:'/tag/'+this.artwork.artwork_id+'/'+inputValue
          }).then(response=>{
            if(response.data!="noSession"){
              if(response.data==true){
                _this.tags.push(inputValue);
                _this.$message({
                  message: '标签添加成功',
                  type: 'success',
                });
              }
            }
          });
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
    reply(index){
      if(this.comments[index].isReplying != true){
        for(var i in this.comments){
          var com = this.comments[i];
          com.isReplying = false;
          Vue.set(this.comments,i,com);
        }
        var com = this.comments[index];
        com.isReplying = true;
        Vue.set(this.comments,index,com);
      }
      else{
        var com = this.comments[index];
        com.isReplying = false;
        Vue.set(this.comments,index,com);
      }
    },
    uploadReply(index) {
      var _this = this;
      axios({
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: 'post',
        url: '/comment/' + this.artworkId+"/"+this.comments[index].comment_id,
        data: "content=" + this.$data.form.reply

      }).then(response => {
        if (response.data != "noSession") {
          var comm = response.data;
          comm.user_avatar = _this.user.user_avatar;
          comm.user_id = _this.user.user_id;
          comm.user_name = _this.user.user_name;
          console.log(_this.comments[index]);
          if(_this.comments[index].reply==null)_this.comments[index].reply=[];
          var re = _this.comments[index];
          re.isReplying=false;
          re.reply.unshift(comm);
          _this.$set(_this.comments,index,re);
        }
      });
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
    window.scrollTo(0,0);
    this.$router.app.colSpan=18;
    var _this = this;
    axios({
      method:'get',
      url:'/artworks/'+this.artworkId,
    }).then(function (response) {
      var data = response.data;
      _this.artwork = data;
      _this.tags = data.label;
      _this.imageList.push(data.artwork_location);
      _this.pageCount = Math.floor((parseInt(data.artwork_commentCount)+19)/20);
      if(_this.pageCount==0)_this.pageCount=1;
      _this.show = true;
    });
    axios({
      method:'get',
      url:'/comment/'+this.artworkId+'/timeorder/'+this.page,
    }).then(function (response) {
      var data = response.data;
      for(var i in data){
        if(data[i].comment_toId==0){
          _this.comments.push(data[i]);
          _this.comments[i].reply=[1];
          _this.comments[i].reply.splice(0,1);
        }
        else{
          for(var j in _this.comments){
            if(_this.comments[j].comment_id==data[i].comment_toId){
              _this.comments[j].reply.push(data[i]);
              break;
            }
          }
        }
      }
    });
    this.user = this.$router.app.user;
    this.$root.$on('user',(a)=>{
      _this.user = '';
      _this.user = a;
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
  border-radius: 15px;
}
.info-block{
  padding: 12px 30px 12px 30px;
  margin: 30px;
  height: auto;
  background: #FFFFFF;
  text-align: left;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin-bottom: 18px;
  margin-top: 0;
  border-radius: 15px;
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
.comment-list-enter-active{
  transition: all 1s;
}
.comment-list-leave-active{
  position: fixed;
  transition: all 0.5s;
}
.comment-list-enter
  /* .list-leave-active for below version 2.1.8 */ {
  opacity: 0;
  transform: scale(0.2,0.2);
}
.comment-list-leave-to{
  opacity: 0;
}
.comment-list-move{
  transition: transform 1s;
}
.comment-other{
  display:inline-block;font-size: 12px;color: #909399;cursor: pointer;text-align: center;
  margin-right: 8px;
}

</style>
