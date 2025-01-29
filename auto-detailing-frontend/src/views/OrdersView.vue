<template>
  <div class="bg-dark-mode-gray">
    <SeparatorComp />
  </div>
  <OrdersPage />
</template>

<script>
import SeparatorComp from '@/components/SeparatorComp.vue'
import OrdersPage from '@/components/OrdersPage.vue'

export default {
  name: 'OrdersView',
  components: {
    SeparatorComp,
    OrdersPage
  }
}
</script>
