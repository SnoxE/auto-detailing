<template>
  <div class="bg-dark-mode-gray min-h-screen">
    <SeparatorComp />
    <MenuSection />
  </div>
</template>

<script>
import SeparatorComp from '@/components/SeparatorComp.vue'
import MenuSection from '@/components/MenuSection.vue'

export default {
  name: 'MenuView',
  components: {
    SeparatorComp,
    MenuSection
  }
}
</script>
