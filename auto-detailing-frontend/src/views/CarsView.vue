<template>
  <div class="bg-dark-mode-gray">
    <SeparatorComp />
  </div>
  <CarsPage />
</template>

<script>
import SeparatorComp from '@/components/SeparatorComp.vue'
import CarsPage from '@/components/CarsPage.vue'

export default {
  name: 'CarsView',
  components: {
    SeparatorComp,
    CarsPage
  }
}
</script>
