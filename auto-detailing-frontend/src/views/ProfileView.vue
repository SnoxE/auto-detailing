<template>
  <div class="min-h-screen bg-dark-mode-gray">
    <SeparatorComp />
    <ProfilePage />
  </div>
</template>

<script>
import ProfilePage from '@/components/ProfilePage.vue'
import SeparatorComp from '@/components/SeparatorComp.vue'

export default {
  name: 'RegisterView',
  components: {
    ProfilePage,
    SeparatorComp
  }
}
</script>
