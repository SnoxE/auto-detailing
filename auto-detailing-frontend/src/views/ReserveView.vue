<template>
  <div class="min-h-screen bg-dark-mode-gray">
    <SeparatorComp />
    <ReserveForm />
  </div>
</template>

<script>
import ReserveForm from '@/components/ReserveForm.vue'
import SeparatorComp from '@/components/SeparatorComp.vue'

export default {
  name: 'ReserveView',
  components: {
    ReserveForm,
    SeparatorComp
  }
}
</script>
