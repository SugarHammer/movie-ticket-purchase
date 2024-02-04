<template>
    <div id="app">
        <router-view/>
        <TabBar v-if="$route.meta.showTabBar"/>
    </div>
</template>

<script>
import TabBar from './components/TabBar'

export default {
    components: {TabBar}
}

</script>

<style>

</style>
