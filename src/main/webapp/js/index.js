const routes =[
    {
        path: '/',
        components:{
            loginpage:httpVueLoader('./vue/login.vue?'+Math.random()),
            home:httpVueLoader('./vue/home.vue?'+Math.random()),
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
            home:httpVueLoader('./vue/artworks.vue?'+Math.random())
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
            home:httpVueLoader('./vue/user.vue?'+Math.random())
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
