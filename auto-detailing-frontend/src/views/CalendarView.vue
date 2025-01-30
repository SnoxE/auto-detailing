<template>
  <div class="bg-dark-mode-gray">
    <SeparatorComp />
  </div>
  <CalendarPage />
</template>

<script>
import SeparatorComp from '@/components/SeparatorComp.vue'
import CalendarPage from '@/components/CalendarPage.vue'

export default {
  name: 'CalendarView',
  components: {
    SeparatorComp,
    CalendarPage
  }
}
</script>
