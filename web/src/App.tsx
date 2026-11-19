import React, { useState } from 'react';
import { Navbar } from './components/ui/Navbar';
import { Footer } from './components/ui/Footer';
import { ProductItem } from './components/ui/ProductCard';
import { CartDrawer, CartItem } from './components/ui/CartDrawer';
import { CustomerHome } from './portals/customer/CustomerHome';
import { ProductListing } from './portals/customer/ProductListing';
import { ProductDetails } from './portals/customer/ProductDetails';
import { CartAndCheckout } from './portals/customer/CartAndCheckout';
import { CustomerAccount } from './portals/customer/CustomerAccount';
import { SellerDashboard } from './portals/seller/SellerDashboard';
import { AdminDashboard } from './portals/admin/AdminDashboard';

export const App: React.FC = () => {
  const [activePortal, setActivePortal] = useState<'customer' | 'seller' | 'admin'>('customer');
  const [customerView, setCustomerView] = useState<'home' | 'listing' | 'details' | 'checkout' | 'account'>('home');
  const [selectedProduct, setSelectedProduct] = useState<ProductItem | null>(null);

  const [cartItems, setCartItems] = useState<CartItem[]>([]);
  const [isCartOpen, setIsCartOpen] = useState(false);

  const allProducts: ProductItem[] = [
    {
      id: 101,
      name: "Sony WH-1000XM5 Wireless Noise Cancelling Headphones",
      basePrice: 24990,
      mrp: 34990,
      mainImageUrl: "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500",
      rating: 4.8,
      reviewCount: 1420,
      categoryName: "Electronics",
      sellerBusinessName: "Sony India Official",
      isFlashSale: true
    },
    {
      id: 102,
      name: "Apple Watch Series 9 GPS 45mm Midnight Aluminum",
      basePrice: 41900,
      mrp: 44900,
      mainImageUrl: "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500",
      rating: 4.9,
      reviewCount: 980,
      categoryName: "Wearables",
      sellerBusinessName: "Apple Retailer Hub"
    },
    {
      id: 103,
      name: "Ergonomic High-Back Executive Mesh Office Chair",
      basePrice: 8499,
      mrp: 16999,
      mainImageUrl: "https://images.unsplash.com/photo-1580481072645-022f9a6d128d?w=500",
      rating: 4.6,
      reviewCount: 310,
      categoryName: "Furniture",
      sellerBusinessName: "GreenSoul Comfort",
      isFlashSale: true
    },
    {
      id: 104,
      name: "Bose SoundLink Flex Portable Bluetooth Speaker",
      basePrice: 12900,
      mrp: 15900,
      mainImageUrl: "https://images.unsplash.com/photo-1545454675-3531b543be5d?w=500",
      rating: 4.7,
      reviewCount: 520,
      categoryName: "Audio",
      sellerBusinessName: "Bose Official"
    }
  ];

  const handleAddToCart = (product: ProductItem) => {
    setCartItems((prev) => {
      const existing = prev.find((item) => item.product.id === product.id);
      if (existing) {
        return prev.map((item) =>
          item.product.id === product.id ? { ...item, quantity: item.quantity + 1 } : item
        );
      }
      return [...prev, { product, quantity: 1 }];
    });
    setIsCartOpen(true);
  };

  const handleUpdateQuantity = (productId: number, qty: number) => {
    if (qty <= 0) {
      handleRemoveItem(productId);
      return;
    }
    setCartItems((prev) =>
      prev.map((item) => (item.product.id === productId ? { ...item, quantity: qty } : item))
    );
  };

  const handleRemoveItem = (productId: number) => {
    setCartItems((prev) => prev.filter((item) => item.product.id !== productId));
  };

  const handleSelectProduct = (product: ProductItem) => {
    setSelectedProduct(product);
    setCustomerView('details');
  };

  const handleSearch = (query: String) => {
    setCustomerView('listing');
  };

  return (
    <div className="min-h-screen bg-surface-bg flex flex-col font-sans">
      {/* Global Navigation Header */}
      <Navbar
        activePortal={activePortal}
        setActivePortal={(portal) => {
          setActivePortal(portal);
          if (portal === 'customer') setCustomerView('home');
        }}
        cartCount={cartItems.reduce((sum, item) => sum + item.quantity, 0)}
        wishlistCount={2}
        onCartClick={() => setIsCartOpen(true)}
        onSearch={handleSearch}
      />

      {/* Main Content View Switcher */}
      <main className="flex-grow">
        {activePortal === 'customer' && (
          <>
            {customerView === 'home' && (
              <CustomerHome
                onAddToCart={handleAddToCart}
                onSelectProduct={handleSelectProduct}
              />
            )}
            {customerView === 'listing' && (
              <ProductListing
                products={allProducts}
                onAddToCart={handleAddToCart}
                onSelectProduct={handleSelectProduct}
              />
            )}
            {customerView === 'details' && selectedProduct && (
              <ProductDetails
                product={selectedProduct}
                onAddToCart={handleAddToCart}
                onBack={() => setCustomerView('home')}
              />
            )}
            {customerView === 'checkout' && (
              <CartAndCheckout
                cartItems={cartItems}
                onUpdateQuantity={handleUpdateQuantity}
                onRemoveItem={handleRemoveItem}
                onBackToShop={() => setCustomerView('home')}
              />
            )}
            {customerView === 'account' && <CustomerAccount />}
          </>
        )}

        {activePortal === 'seller' && <SellerDashboard />}
        {activePortal === 'admin' && <AdminDashboard />}
      </main>

      {/* Slide-Over Cart Drawer */}
      <CartDrawer
        isOpen={isCartOpen}
        onClose={() => setIsCartOpen(false)}
        cartItems={cartItems}
        onUpdateQuantity={handleUpdateQuantity}
        onRemoveItem={handleRemoveItem}
        onProceedToCheckout={() => {
          setIsCartOpen(false);
          setCustomerView('checkout');
        }}
      />

      {/* Footer */}
      <Footer />
    </div>
  );
};

export default App;
