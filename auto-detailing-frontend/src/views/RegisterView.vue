<template>
  <div class="bg-dark-mode-gray min-h-screen">
    <SeparatorComp />
    <RegisterForm />
  </div>
</template>

<script>
import RegisterForm from '@/components/RegisterForm.vue'
import SeparatorComp from '@/components/SeparatorComp.vue'

export default {
  name: 'RegisterView',
  components: {
    RegisterForm,
    SeparatorComp
  }
}
</script>
