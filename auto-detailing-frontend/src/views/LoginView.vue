<template>
  <div class="bg-dark-mode-gray min-h-screen">
    <SeparatorComp />
    <LoginForm />
  </div>
</template>

<script>
import LoginForm from '@/components/LoginForm.vue'
import SeparatorComp from '@/components/SeparatorComp.vue'

export default {
  name: 'LoginView',
  components: {
    LoginForm,
    SeparatorComp
  }
}
</script>
