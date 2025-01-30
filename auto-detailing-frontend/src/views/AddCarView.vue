<template>
  <div class="min-h-screen bg-dark-mode-gray">
    <SeparatorComp />
    <AddCarForm />
  </div>
</template>

<script>
import AddCarForm from '@/components/AddCarForm.vue'
import SeparatorComp from '@/components/SeparatorComp.vue'

export default {
  name: 'AddCarView',
  components: {
    AddCarForm,
    SeparatorComp
  }
}
</script>
