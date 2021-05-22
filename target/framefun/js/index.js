const routes =[
    {
        path: '/',
        components:{
            loginpage:httpVueLoader('./vue/login.vue?'+Math.random()),
            home:httpVueLoader('./vue/main.vue?'+Math.random()),
        }
    },
    {
        path: '/home/:group',
        components:{
            home:httpVueLoader('./vue/home.vue?'+Math.random())
        }
    },
    {
        path: '/login',
        components:{
            loginpage:httpVueLoader('./vue/login.vue?'+Math.random())
        }
    },
    {
        path:'/register',
        components:{
            loginpage:httpVueLoader('./vue/register.vue?'+Math.random())
        }
    },
    {
        path:'/upload',
        components: {
            home:httpVueLoader('./vue/upload.vue?'+Math.random())
        },
        beforeEnter: (to, from, next) => {
            console.log("dfd")
            router.app.loadingMain=true;
            next();
        }
    },
    {
        path:'/artworks/:id',
        components: {
            home:httpVueLoader('./vue/artworks.vue?'+Math.random()),
            aside:httpVueLoader('./vue/artwork_user.vue?'+Math.random())
        }
    },
    {
        path:'/collections/:page',
        components: {
            home:httpVueLoader('./vue/collection.vue?'+Math.random())
        }
    },
    {
        path:'/myworks/:page',
        components: {
            home:httpVueLoader('./vue/myworks.vue?'+Math.random())
        }
    },
    {
        path:'/user',
        components: {
            home:httpVueLoader('./vue/user.vue?'+Math.random()),
        }
    },
    {
        path:'/tag-search/:tagname/:group/:page',
        components: {
            home:httpVueLoader('./vue/tagSearch.vue?'+Math.random()),
        }
    },
    {
        path:'/follow/artwork',
        components: {
            home:httpVueLoader('./vue/followArtwork.vue?'+Math.random()),
        }
    },
    {
        path:'/hot/artwork',
        components: {
            home:httpVueLoader('./vue/hotArtwork.vue?'+Math.random()),
        }
    },
    {
        path:'/artist/:id',
        components: {
            home:httpVueLoader('./vue/artist.vue?'+Math.random()),
        }
    },
    {
        path:'/artist/collection/:id/:page',
        components: {
            home:httpVueLoader('./vue/othersCollection.vue?'+Math.random()),
        }
    },
    {
        path:'/artist/artwork/:id/:page',
        components: {
            home:httpVueLoader('./vue/othersArtwork.vue?'+Math.random()),
        }
    },
    {
        path:'/follow/following/:id/:page',
        components: {
            home:httpVueLoader('./vue/followingList.vue?'+Math.random()),
        }
    },
    {
        path:'/follow/follower/:id/:page',
        components: {
            home:httpVueLoader('./vue/followerList.vue?'+Math.random()),
        }
    }
];
const router = new VueRouter({
    //mode:'history',
    routes // (缩写) 相当于 routes: routes
});
export {
    router
}
